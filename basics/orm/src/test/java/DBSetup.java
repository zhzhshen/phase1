import config.ConnectionConfig;
import migration.MigrationManager;
import org.junit.After;
import org.junit.Before;
import util.MappingUtil;

public class DBSetup {
    final String BASE_URL = "jdbc:mysql://localhost:3306";
    final String DB_NAME = "orm_test";
    final String USER_NAME = "mysql";
    final String PASSWORD = "mysql";
    MigrationManager migrationManager;
    ConnectionConfig connectionConfig;
    MappingUtil mappingUtil;

    @Before
    public void before() {
        connectionConfig = new ConnectionConfig(BASE_URL, DB_NAME, USER_NAME, PASSWORD);
        migrationManager = new MigrationManager(connectionConfig);
        mappingUtil = new MappingUtil(connectionConfig);
        migrationManager.migrate();
    }

    @After
    public void after() {
        migrationManager.clean();
    }
}
