import mapping.Criterion;
import model.User;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class SingleTableTest extends DBSetup {
    private String EXISTING_ID = "1";
    private String INEXIST_ID = "2";
    private User EXISTING_USER = new User(EXISTING_ID, "Sid", "Shen", 24);
    private User INEXIST_USER = new User(INEXIST_ID, "Zhangzhe", "Shen", 30);

    @Test
    public void should_fail_to_find_by_inexist_id () {
        User user = mappingUtil.get(User.class, INEXIST_ID);

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_id () {
        User user = mappingUtil.get(User.class, EXISTING_ID);

        assertThat(user, notNullValue());
        assertThat(user.userId, is(EXISTING_ID));
        assertThat(user.firstName, is("Sid"));
        assertThat(user.lastName, is("Shen"));
        assertThat(user.age, is(24));
    }

    @Test
    public void should_fail_to_find_by_inexist_field () {
        User user = mappingUtil.from(User.class).criterion(Criterion.eq("firstName", "Zhangzhe")).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_existing_field () {
        User user = mappingUtil.from(User.class).criterion(Criterion.eq("firstName", "Sid")).get();

        assertThat(user, notNullValue());
    }

    @Test
    public void should_find_by_greater_than_field_return_empty () {
        User user = mappingUtil.from(User.class).criterion(Criterion.greater("age", 25)).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_greater_than_field () {
        User user = mappingUtil.from(User.class).criterion(Criterion.greater("age", 20)).get();

        assertThat(user, notNullValue());
    }

    @Test
    public void should_find_by_less_than_field_return_empty () {
        User user = mappingUtil.from(User.class).criterion(Criterion.less("age", 20)).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_less_than_field () {
        User user = mappingUtil.from(User.class).criterion(Criterion.less("age", 25)).get();

        assertThat(user, notNullValue());
    }

    @Test
    public void should_success_to_create_new_record () throws SQLException {
        mappingUtil.save(INEXIST_USER);

        assertThat(mappingUtil.get(User.class, "2"), is(INEXIST_USER));
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void should_fail_to_create_new_record_existin_id () throws SQLException {
        mappingUtil.save(EXISTING_USER);
    }

    @Test
    public void should_fail_to_update_inexist_record () throws SQLException {
        mappingUtil.update(INEXIST_USER);

        assertThat(mappingUtil.get(User.class, INEXIST_ID), nullValue());
    }

    @Test
    public void should_success_to_update_existing_record () throws SQLException {
        User user = new User(EXISTING_ID, "Sid", "Shen", 30);
        assertThat(mappingUtil.get(User.class, EXISTING_ID), not(user));

        mappingUtil.update(user);

        assertThat(mappingUtil.get(User.class, EXISTING_ID), is(user));
    }

    @Test
    public void should_fail_to_delete_inexist_record () throws SQLException {
        mappingUtil.delete(INEXIST_USER);

        assertThat(mappingUtil.get(User.class, INEXIST_ID), nullValue());
    }

    @Test
    public void should_fail_to_delete_existING_record () throws SQLException {
        assertThat(mappingUtil.get(User.class, EXISTING_ID), notNullValue());

        mappingUtil.delete(EXISTING_USER);

        assertThat(mappingUtil.get(User.class, EXISTING_ID), nullValue());
    }
}
