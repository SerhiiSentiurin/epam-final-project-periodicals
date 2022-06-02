package periodicals.epam.com.project.logic.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import periodicals.epam.com.project.logic.entity.dto.ReaderCreateDTO;
import periodicals.epam.com.project.logic.logicExeption.ReaderException;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Log4j2
@RequiredArgsConstructor
public class ReaderDAO {
    private final DataSource dataSource;


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
