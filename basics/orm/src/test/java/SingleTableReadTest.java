import mapping.Criterion;
import model.User;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class SingleTableReadTest extends DBSetup {
    private String EXISTING_ID = "1";
    private String INEXIST_ID = "2";

    @Test
    public void should_fail_to_find_by_inexist_id () {
        User user = finderUtil.get(User.class, INEXIST_ID);

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_id () {
        User user = finderUtil.get(User.class, EXISTING_ID);

        assertThat(user, notNullValue());
        assertThat(user.userId, is(EXISTING_ID));
        assertThat(user.firstName, is("Sid"));
        assertThat(user.lastName, is("Shen"));
        assertThat(user.age, is(24));
    }

    @Test
    public void should_fail_to_find_by_inexist_field () {
        User user = finderUtil.from(User.class).criterion(Criterion.eq("firstName", "Zhangzhe")).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_existing_field () {
        User user = finderUtil.from(User.class).criterion(Criterion.eq("firstName", "Sid")).get();

        assertThat(user, notNullValue());
    }

    @Test
    public void should_find_by_greater_than_field_return_empty () {
        User user = finderUtil.from(User.class).criterion(Criterion.greater("age", 25)).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_greater_than_field () {
        User user = finderUtil.from(User.class).criterion(Criterion.greater("age", 20)).get();

        assertThat(user, notNullValue());
    }

    @Test
    public void should_find_by_less_than_field_return_empty () {
        User user = finderUtil.from(User.class).criterion(Criterion.less("age", 20)).get();

        assertThat(user, nullValue());
    }

    @Test
    public void should_success_to_find_by_less_than_field () {
        User user = finderUtil.from(User.class).criterion(Criterion.less("age", 25)).get();

        assertThat(user, notNullValue());
    }
}
