package periodicals.epam.com.project.logic.dao;

import liquibase.pro.packaged.P;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import periodicals.epam.com.project.logic.entity.Periodical;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


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
        String selectPeriodicalsByName = "SELECT * FROM periodical WHERE name LIKE ?";
        List<Periodical> periodicals = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPeriodicalsByName)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name1 = resultSet.getString("name");
                    String topic = resultSet.getString("topic");
                    double cost = resultSet.getDouble("cost");
                    String description = resultSet.getString("description");
                    Periodical periodical = new Periodical(id, name1, topic, cost, description);
                    periodicals.add(periodical);
                }
            }
        }
        return periodicals;
    }

    @SneakyThrows
    public List<Periodical> getPeriodicalsByReaderId(Long readerId) {
        String selectPeriodicalsByReaderId = "SELECT * FROM periodical JOIN periodicals ON periodical.id = periodicals.periodical_id WHERE reader_id =?";
        List<Periodical> periodicals = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPeriodicalsByReaderId)) {
            preparedStatement.setLong(1, readerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long periodicalId = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String topic = resultSet.getString("topic");
                    double cost = resultSet.getDouble("cost");
                    String description = resultSet.getString("description");
                    Periodical periodical = new Periodical(periodicalId, name, topic, cost, description);
                    periodicals.add(periodical);
                }
            }
        }
        return periodicals;
    }

    @SneakyThrows
    public List<Periodical> getPeriodicalsForSubscribing(List<Long> listPeriodicalId) {
        List<Periodical> periodicalsForSubscribe = new ArrayList<>();
        final String sqlToGetPeriodicalsForSubscribing = buildSqlToGetPeriodicalsForSubscribing(listPeriodicalId);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(sqlToGetPeriodicalsForSubscribing)) {
                while (resultSet.next()) {
                    long perIdForSubs = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String topic = resultSet.getString("topic");
                    double cost = resultSet.getDouble("cost");
                    String description = resultSet.getString("description");
                    Periodical periodical = new Periodical(perIdForSubs, name, topic, cost, description);
                    periodicalsForSubscribe.add(periodical);
                }
            }
        }
        return periodicalsForSubscribe;


//        String sql = "SELECT * FROM periodical left join periodicals ON periodicals.periodical_id = periodical.id WHERE reader_id IS NULL OR NOT periodical_id = ?"; // нужно чтоб запрос выполнялся несколько разб но ситтелось все один раз
//        //String sql = "SELECT * FROM periodical join periodicals ON periodical.id = periodicals.periodical_id where reader_id <> ?";
//        List<Long> listPeriodicalId = getPeriodicalIdByReaderId(readerId);
//        Iterator<Long> iterator = listPeriodicalId.iterator();
//
//        List<Periodical> periodicalsForSubscribe = new ArrayList<>();
//
//            try (Connection connection = dataSource.getConnection();
//                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                Long periodicalId = iterator.next();
//                preparedStatement.setLong(1, periodicalId);
//
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    while (resultSet.next()) {
//                        long perIdForSubs = resultSet.getLong("id");
//                        String name = resultSet.getString("name");
//                        String topic = resultSet.getString("topic");
//                        double cost = resultSet.getDouble("cost");
//                        String description = resultSet.getString("description");
//                        Periodical periodical = new Periodical(perIdForSubs, name, topic, cost, description);
//                        periodicalsForSubscribe.add(periodical);
//                    }
//                }
//            }
//
//        return periodicalsForSubscribe;
    }

    private String buildSqlToGetPeriodicalsForSubscribing(List<Long> listPeriodicalId) {
        final StringBuilder sqlBuilder = new StringBuilder("SELECT distinct id, name, topic, cost, description FROM periodical");
        if (!listPeriodicalId.isEmpty()) {
            sqlBuilder.append(" left join periodicals ON periodicals.periodical_id = periodical.id WHERE reader_id IS NULL OR ");
            final Iterator<Long> iterator = listPeriodicalId.iterator();
            while (iterator.hasNext()) {
                sqlBuilder.append("NOT periodical_id = ").append(iterator.next());
                if (iterator.hasNext()) {
                    sqlBuilder.append(" and ");
                }
            }
        }
        return sqlBuilder.toString();
    }

    @SneakyThrows
    public List<Long> getPeriodicalIdByReaderId(Long readerId) {
        String query = "SELECT periodical_id FROM periodicals WHERE reader_id = ?";
        List<Long> setOfPeriodicalId = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, readerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long periodicalId = resultSet.getLong("periodical_id");
                    setOfPeriodicalId.add(periodicalId);
                }
            }
        }
        return setOfPeriodicalId;
    }
}



