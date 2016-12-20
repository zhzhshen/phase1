import model.User;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SingleTableCreateTest extends DBSetup {
    @Test
    public void should_success_to_create_new_record () {
        User user = new User("2", "Zhangzhe", "Shen", 30);
        mappingUtil.save(user);

        assertThat(mappingUtil.get(User.class, "2"), is(user));
    }
}
