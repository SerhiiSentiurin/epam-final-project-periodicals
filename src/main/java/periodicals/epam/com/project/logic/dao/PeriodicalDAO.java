package periodicals.epam.com.project.logic.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import periodicals.epam.com.project.logic.entity.Periodical;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class PeriodicalDAO {

    private final DataSource dataSource;

    @SneakyThrows
    public List<Periodical> getAllPeriodicals() {
        String query = "SELECT * FROM periodical";
        List<Periodical> listOfPeriodicals = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String topic = resultSet.getString("topic");
                double cost = resultSet.getDouble("cost");
                String description = resultSet.getString("description");
                Periodical periodical = new Periodical(id, name, topic, cost, description);
                listOfPeriodicals.add(periodical);

//                Periodical periodical = new Periodical();
//                periodical.setId(resultSet.getLong("id"));
//                periodical.setName(resultSet.getString("name"));
//                periodical.setTopic(resultSet.getString("topic"));
//                periodical.setCost(resultSet.getDouble("cost"));
//                periodical.setDescription(resultSet.getString("description"));
//                listOfPeriodicals.add(periodical);
            }
            return listOfPeriodicals;
        }
    }

    @SneakyThrows
    public List<Periodical> getPeriodicalsByTopic(String topic) {
        String selectPeriodicalsByTopic = "SELECT * FROM periodical WHERE topic = ?";
        List<Periodical> periodicals = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPeriodicalsByTopic)) {
            preparedStatement.setString(1, topic);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    double cost = resultSet.getDouble("cost");
                    String description = resultSet.getString("description");
                    Periodical periodical = new Periodical(id, name, topic, cost, description);
                    periodicals.add(periodical);
                }
            }
        }
        return periodicals;
    }

    @SneakyThrows
    public List<Periodical> getPeriodicalByName(String name) {
        String selectPeriodicalsByName = "SELECT * FROM periodical WHERE name = ?";
        List<Periodical> periodicals = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPeriodicalsByName)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String topic = resultSet.getString("topic");
                    double cost = resultSet.getDouble("cost");
                    String description = resultSet.getString("description");
                    Periodical periodical = new Periodical(id, name, topic, cost, description);
                    periodicals.add(periodical);
                }
            }
        }
        return periodicals;
    }

    public List<Periodical> getPeriodicalsByReaderId(Long id) {
        String selectPeriodicalsByReaderId = "SELECT * FROM periodical join periodicals ON periodical.id = periodicals.periodical_id where reader_id = ?;";
        List<Periodical> periodicals = new ArrayList<>();


        return periodicals;
    }


}
