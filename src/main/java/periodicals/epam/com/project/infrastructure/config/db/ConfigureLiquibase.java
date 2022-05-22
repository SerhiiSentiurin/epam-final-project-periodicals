package periodicals.epam.com.project.infrastructure.config.db;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import periodicals.epam.com.project.infrastructure.config.ConfigLoader;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
public class ConfigureLiquibase {
    private final DataSource dataSource;

    @SneakyThrows
    public void updateDatabase(ConfigLoader configLoader) {
        String change_log_file = configLoader.getConfigs().get("change_log_file");
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase(change_log_file, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}
