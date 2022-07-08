package periodicals.epam.com.project.logic.dao;

import com.oracle.wls.shaded.org.apache.regexp.RE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import periodicals.epam.com.project.logic.entity.dto.PrepaymentDTO;
import periodicals.epam.com.project.logic.logicExeption.ReaderException;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrepaymentDAOTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private PreparedStatement preparedStatement1;
    @Mock
    private Statement statement;

    @InjectMocks
    PrepaymentDAO dao;

    private static final Long READER_ID = 1L;
    private static final Long PERIODICAL_ID = 1L;
    private static final PrepaymentDTO dto = new PrepaymentDTO(30, READER_ID,PERIODICAL_ID,50d);
    private static final String ADD_SUBSCRIPTION = "INSERT INTO periodicals (reader_id, periodical_id) VALUES (?,?)";
    private static final String UPDATE_ACCOUNT = "UPDATE account INNER JOIN reader ON account.id = reader.account_id SET account.amount = ? WHERE reader.id = " + dto.getReaderId();
    private static final String UPDATE_PREPAYMENT = "INSERT INTO prepayment (start_date, due_date, periodical_id, reader_id) VALUES (curdate(), adddate(start_date, INTERVAL " + dto.getDurationOfSubscription() +" DAY), " + dto.getPeriodicalId() + ", " + dto.getReaderId() + ");";
    private static final String DELETE_FROM_PERIODICALS = "DELETE FROM periodicals WHERE reader_id = ? AND periodical_id = ?";
    private static final String DELETE_FROM_PREPAYMENTS = "DELETE FROM prepayment WHERE reader_id = ? AND periodical_id = ?";

    @Before
    public void setConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    //дичь
    @Test
    public void addSubscriptionWhenTransactionCommitTest()throws SQLException {
        when(connection.prepareStatement(ADD_SUBSCRIPTION)).thenReturn(preparedStatement);
        when(connection.prepareStatement(UPDATE_ACCOUNT)).thenReturn(preparedStatement1);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.execute(UPDATE_PREPAYMENT)).thenReturn(true);

        PrepaymentDTO resultDto = dao.addSubscription(dto);
        assertNotNull(resultDto);
        assertEquals(dto,resultDto);

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).setLong(1,dto.getReaderId());
        verify(preparedStatement).setLong(2,dto.getPeriodicalId());
        verify(preparedStatement).execute();
        verify(preparedStatement1).setDouble(1,dto.getAmountOfMoney());
        verify(preparedStatement1).execute();
        verify(statement).execute(UPDATE_PREPAYMENT);
        verify(connection).commit();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    public void addSubscriptionWhenTransactionRollbackTest()throws SQLException{

    }

    @Test
    public void deleteSubscriptionWhenTransactionCommitTest()throws SQLException{
        when(connection.prepareStatement(DELETE_FROM_PERIODICALS)).thenReturn(preparedStatement);
        when(connection.prepareStatement(DELETE_FROM_PREPAYMENTS)).thenReturn(preparedStatement1);

        boolean result = dao.deleteSubscription(READER_ID,PERIODICAL_ID);
        assertTrue(result);

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).setLong(1,READER_ID);
        verify(preparedStatement).setLong(2,PERIODICAL_ID);
        verify(preparedStatement).execute();
        verify(preparedStatement1).setLong(1,READER_ID);
        verify(preparedStatement1).setLong(2,PERIODICAL_ID);
        verify(preparedStatement1).execute();
        verify(connection).commit();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test(expected = ReaderException.class)
    public void deleteSubscriptionWhenTransactionRollbackTest()throws SQLException{
        when(connection.prepareStatement(DELETE_FROM_PERIODICALS)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenThrow(SQLException.class);
        dao.deleteSubscription(READER_ID,PERIODICAL_ID);

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).setLong(1,READER_ID);
        verify(preparedStatement).setLong(2,PERIODICAL_ID);
        verify(preparedStatement).execute();
        verify(preparedStatement).close();
        verify(connection).close();
        verify(connection).rollback();
    }
}
