import config.ConnectionConfig;
import migration.MigrationManager;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import mapping.Criterion;
import util.FinderUtil;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class SingleTableMappingTest {
    private final String BASE_URL = "jdbc:mysql://localhost:3306";
    private final String DB_NAME = "orm_test";
    private final String USER_NAME = "mysql";
    private final String PASSWORD = "mysql";
    private String EXISTING_ID = "1";
    private String INEXIST_ID = "2";
    private MigrationManager migrationManager;
    private FinderUtil finderUtil;
    private ConnectionConfig connectionConfig;

    @Before
    public void before() {
        connectionConfig = new ConnectionConfig(BASE_URL, DB_NAME, USER_NAME, PASSWORD);
        migrationManager = new MigrationManager(connectionConfig);
        finderUtil = new FinderUtil(new ConnectionConfig(BASE_URL, DB_NAME, USER_NAME, PASSWORD));

        migrationManager.migrate();
    }

    @Test
    public void should_fail_to_find_by_inexist_id () throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        User user = finderUtil.get(User.class, INEXIST_ID);

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_id () throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        User user = finderUtil.get(User.class, EXISTING_ID);

        assertThat(user, notNullValue());
        assertThat(user.userId, is(EXISTING_ID));
        assertThat(user.firstName, is("Sid"));
        assertThat(user.lastName, is("Shen"));
        assertThat(user.age, is(24));
    }

    @Test
    public void should_fail_to_find_by_inexist_field () throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        User user = finderUtil.from(User.class).criterion(Criterion.eq("firstName", "Zhangzhe")).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_existing_field () throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        User user = finderUtil.from(User.class).criterion(Criterion.eq("firstName", "Sid")).get();

        assertThat(user, notNullValue());
    }

    @After
    public void after() {
        migrationManager.clean();
    }
}
