import model.User;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SingleTableCreateTest extends DBSetup {
    @Test
    public void should_success_to_create_new_record () throws SQLException {
        User user = new User("2", "Zhangzhe", "Shen", 30);
        mappingUtil.save(user);

        assertThat(mappingUtil.get(User.class, "2"), is(user));
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void should_fail_to_create_new_record_existin_id () throws SQLException {
        User user = new User("1", "Zhangzhe", "Shen", 30);

        mappingUtil.save(user);

        User actual = mappingUtil.get(User.class, "1");
        assertThat(actual.firstName, is("Sid"));
    }
}
