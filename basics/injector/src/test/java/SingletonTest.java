import com.sid.injector.Container;
import org.junit.Test;
import resources.singleton.SingletonBook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SingletonTest {
    @Test
    public void should_() {
        Container container = new Container();
        container.register(SingletonBook.class);

        SingletonBook book1 = container.resolve(SingletonBook.class);
        SingletonBook book2 = container.resolve(SingletonBook.class);

        assertThat(book1, is(book2));
    }
}
