package periodicals.epam.com.project.logic.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import periodicals.epam.com.project.logic.entity.Account;
import periodicals.epam.com.project.logic.entity.Periodical;
import periodicals.epam.com.project.logic.entity.Reader;
import periodicals.epam.com.project.logic.entity.dto.AccountDTO;
import periodicals.epam.com.project.logic.entity.dto.ReaderCreateDTO;
import periodicals.epam.com.project.logic.logicExeption.ReaderException;


import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class ReaderDAO {
    private final DataSource dataSource;

    @SneakyThrows
    public Optional<Reader> getReaderById(Long id) {
        String sql = "select user.login, reader.account_id, reader.lock, account.amount from user join reader on user.id = reader.id join account on reader.account_id = account.id where user.id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String login = resultSet.getString("login");
                    boolean lock = resultSet.getBoolean("lock");
                    long accountId = resultSet.getLong("account_id");
                    double amountOfMoney = resultSet.getDouble("amount");
                    Account account = new Account(accountId, amountOfMoney);
                    Reader reader = new Reader();
                    reader.setId(id);
                    reader.setLogin(login);
                    reader.setLock(lock);
                    reader.setAccount(account);
                    return Optional.of(reader);
                }
            }
        }
        return Optional.empty();
    }

    public ReaderCreateDTO insertReader(ReaderCreateDTO readerCreateDTO) {
        String insertIntoUser = "INSERT INTO user (login, password, role) VALUES (?,?,?);";
        String insertIntoAccount = "insert into account (amount) value (0)";
        String insertIntoReader = "INSERT INTO READER (id,account_id) values (?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertIntoUser, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, readerCreateDTO.getLogin());
            preparedStatement.setString(2, readerCreateDTO.getPassword());
            preparedStatement.setString(3, readerCreateDTO.getUserRole().toString());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(insertIntoAccount, Statement.RETURN_GENERATED_KEYS);
                        ResultSet resultSet1 = statement.getGeneratedKeys();
                        if (resultSet1.next()) {
                            long accountId = resultSet1.getLong(1);
                            try (PreparedStatement preparedStatement1 = connection.prepareStatement(insertIntoReader)) {
                                preparedStatement1.setLong(1, id);
                                preparedStatement1.setLong(2, accountId);
                                preparedStatement1.execute();
                                connection.commit();
                            }
                        }
                    }
                    readerCreateDTO.setId(id);
                }
            }
        } catch (Exception e) {
            rollback(connection);
            log.error(e.getMessage());
            throw new ReaderException("Transaction failed with create reader!");
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return readerCreateDTO;
    }


//    public AccountDTO addSubscription(AccountDTO dto) {
//        String addSubscription = "INSERT INTO periodicals (reader_id, periodical_id) VALUES (?,?)";
//        String updateAccount = "update account inner join reader on account.id = reader.account_id set account.amount = ? where reader.id = " + dto.getReaderId() + ";";
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        if (getPeriodicalCost(dto.getPeriodicalId()) > getAmountFromAccount(dto.getReaderId())) {
//            log.error("reader have not enough money on the account");
//            throw new ReaderException("Not enough money on the account");
//        } else {
//            dto.setAmountOfMoney(getAmountFromAccount(dto.getReaderId()) - getPeriodicalCost(dto.getPeriodicalId()));
//        }
//        try {
//            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);
//            preparedStatement = connection.prepareStatement(addSubscription);
//            preparedStatement.setLong(1, dto.getReaderId());
//            preparedStatement.setLong(2, dto.getPeriodicalId());
//            preparedStatement.execute();
//            try (PreparedStatement preparedStatement1 = connection.prepareStatement(updateAccount)) {
//                preparedStatement1.setDouble(1, dto.getAmountOfMoney());
//                preparedStatement1.execute();
//                connection.commit();
//                return dto;
//            }
//        } catch (Exception e) {
//            rollback(connection);
//            log.error(e.getMessage());
//            throw new ReaderException("Transaction failed with add subscription");
//        } finally {
//            close(preparedStatement);
//            close(connection);
//        }
//    }
//
//    @SneakyThrows
//    private Double getPeriodicalCost(Long periodicalId) {
//        String selectCost = "SELECT cost FROM periodical WHERE id = ? ";
//        Periodical periodical = new Periodical();
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(selectCost)) {
//            preparedStatement.setLong(1, periodicalId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                double cost = resultSet.getDouble("cost");
//                periodical.setCost(cost);
//            }
//        }
//        return periodical.getCost();
//    }
//
//    @SneakyThrows
//    private Double getAmountFromAccount(Long readerId) {
//        String selectAccountAmount = "select account.id, amount from account join reader on reader.account_id = account.id where reader.id = ?";
//        Account readerAccount = new Account();
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(selectAccountAmount)) {
//            preparedStatement.setLong(1, readerId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                long accId = resultSet.getLong("id");
//                double amount = resultSet.getDouble("amount");
//                readerAccount.setId(accId);
//                readerAccount.setAmountOfMoney(amount);
//            }
//        }
//        return readerAccount.getAmountOfMoney();
//    }


    @SneakyThrows
    private void rollback(Connection connection) {
        if (connection != null)
            connection.rollback();
    }

    @SneakyThrows
    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
    }
}
