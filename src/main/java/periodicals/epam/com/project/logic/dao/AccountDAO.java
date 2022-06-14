package periodicals.epam.com.project.logic.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import periodicals.epam.com.project.logic.entity.Account;
import periodicals.epam.com.project.logic.entity.dto.AccountDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Log4j2
@RequiredArgsConstructor
public class AccountDAO {
    private final DataSource dataSource;
    @SneakyThrows
    public boolean topUpAccountAmount(AccountDTO dto) {
        String sql = "update account inner join reader on account.id = reader.account_id set account.amount = ? where reader.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, dto.getAmountOfMoney() + getAmountOfMoneyByReaderId(dto));
            preparedStatement.setLong(2, dto.getReaderId());
            return preparedStatement.execute();
        }
    }

    @SneakyThrows
    public Double getAmountOfMoneyByReaderId (AccountDTO dto){
        String selectAccountAmount = "select account.id, amount from account join reader on reader.account_id = account.id where reader.id = ?";
        Account readerAccount = new Account();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAccountAmount)) {
            preparedStatement.setLong(1, dto.getReaderId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long accId = resultSet.getLong("id");
                double amount = resultSet.getDouble("amount");
                readerAccount.setId(accId);
                readerAccount.setAmountOfMoney(amount);
            }
        }
        return readerAccount.getAmountOfMoney();
    }


}
