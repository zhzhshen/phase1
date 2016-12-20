import config.ConnectionConfig;
import migration.MigrationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MigrationTest {
    private final String BASE_URL = "jdbc:mysql://localhost:3306";
    private final String DB_NAME = "orm_test";
    private final String USER_NAME = "mysql";
    private final String PASSWORD = "mysql";
    private MigrationManager migrationManager;

    @Before
    public void before() throws SQLException {
        migrationManager = new MigrationManager(new ConnectionConfig(BASE_URL, DB_NAME, USER_NAME, PASSWORD));
    }

    @Test(expected = SQLException.class)
    public void should_fail_to_access_database_without_migration () throws SQLException {
        DriverManager.getConnection(BASE_URL + "/" + DB_NAME, USER_NAME, PASSWORD);
    }

    @Test
    public void should_success_to_migrate () throws SQLException {
        migrationManager.migrate();
        Connection connection = DriverManager.getConnection(BASE_URL + "/" + DB_NAME, USER_NAME, PASSWORD);
        ResultSet userTable = connection.getMetaData().getTables(DB_NAME, null, "Users", new String[]{"TABLE"});
        assertThat(userTable.next(), is(true));
    }

    @Test(expected = SQLException.class)
    public void should_success_to_clean () throws SQLException {
        should_success_to_migrate();
        migrationManager.clean();

        DriverManager.getConnection(BASE_URL + "/" + DB_NAME, USER_NAME, PASSWORD);
    }

    @After
    public void after() {
        migrationManager.clean();
    }
}
