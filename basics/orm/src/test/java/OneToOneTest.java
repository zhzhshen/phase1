import model.User;
import model.UserInfo;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class OneToOneTest extends DBSetup {
    @Test
    public void should_success_to_create_new_record () throws SQLException {
        User user = new User("2", "Zhangzhe", "Shen", 30);
        UserInfo userInfo = new UserInfo("1", "13800000000", "Address 1");
        user.info(userInfo);

        mappingUtil.save(user);

        assertThat(mappingUtil.get(UserInfo.class, "1"), notNullValue());
//        assertThat(mappingUtil.get(User.class, "1").userInfo, is(userInfo));
    }
}
