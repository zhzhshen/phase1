import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

public class FlywayTest {
    private final String BASE_URL = "jdbc:mysql://localhost:3306";
    private final String DB_NAME = "orm_test";
    private final String USER_NAME = "mysql";
    private final String PASSWORD = "mysql";
    private Flyway flyway;

    @Before
    public void before() throws SQLException {
        flyway = new Flyway();
        flyway.setDataSource(BASE_URL, USER_NAME, PASSWORD);
        flyway.setSchemas(DB_NAME);
    }

    @Test(expected = SQLException.class)
    public void should_fail_to_access_database_without_migration () throws SQLException {
        DriverManager.getConnection(BASE_URL + "/" + DB_NAME, USER_NAME, PASSWORD);
    }

    @Test
    public void should_success_to_migrate () throws SQLException {
        flyway.migrate();
        DriverManager.getConnection(BASE_URL + "/" + DB_NAME, USER_NAME, PASSWORD);
    }

    @Test(expected = SQLException.class)
    public void should_success_to_clean () throws SQLException {
        should_success_to_migrate();
        flyway.clean();

        DriverManager.getConnection(BASE_URL + "/" + DB_NAME, USER_NAME, PASSWORD);
    }

    @After
    public void after() {
        flyway.clean();
    }
}
