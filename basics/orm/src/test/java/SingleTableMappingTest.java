import config.ConnectionConfig;
import migration.MigrationManager;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.FinderUtil;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class SingleTableMappingTest {
    private final String BASE_URL = "jdbc:mysql://localhost:3306";
    private final String DB_NAME = "orm_test";
    private final String USER_NAME = "mysql";
    private final String PASSWORD = "mysql";
    private MigrationManager migrationManager;

    @Before
    public void before() {
        migrationManager = new MigrationManager(new ConnectionConfig(BASE_URL, DB_NAME, USER_NAME, PASSWORD));
        migrationManager.migrate();
    }

    @Test
    public void should_success_to_find_by_id () throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        User user = new FinderUtil(new ConnectionConfig(BASE_URL, DB_NAME, USER_NAME, PASSWORD)).get(User.class, "1");
        assertThat(user, notNullValue());
        assertThat(user.userId, is("1"));
        assertThat(user.firstName, is("Sid"));
        assertThat(user.lastName, is("Shen"));
        assertThat(user.age, is(24));
    }

    @After
    public void after() {
        migrationManager.clean();
    }
}
