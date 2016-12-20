import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MigrationTest extends DBSetup{

    @Test
    public void should_success_to_migrate () throws SQLException {
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

}
