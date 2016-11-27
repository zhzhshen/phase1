import com.sid.injector.Container;
import org.junit.Test;
import resources.provider.TwoBookShelf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class ProviderTest {

    @Test
    public void should_inject_when_annotated_on_multi_method() {
        Container container = new Container();
        container.register(TwoBookShelf.class);

        TwoBookShelf shelf = container.resolve(TwoBookShelf.class);

        assertThat(shelf.getBook(), is(notNullValue()));
        assertThat(shelf.getBookTwo(), is(notNullValue()));
    }
}
