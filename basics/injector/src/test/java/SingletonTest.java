import com.sid.injector.Container;
import org.junit.Test;
import resources.singleton.SingletonBook;
import resources.singleton.SingletonBookShelf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SingletonTest {
    @Test
    public void should_inject_singleton_instance_when_singleton_annotated() {
        Container container = new Container();
        container.register(SingletonBook.class);

        SingletonBook book1 = container.resolve(SingletonBook.class);
        SingletonBook book2 = container.resolve(SingletonBook.class);

        assertThat(book1, is(book2));
    }

    @Test
    public void should_inject_same_instance_when_field_class_is_singleton_annotated() {
        Container container = new Container();
        container.register(SingletonBookShelf.class);

        SingletonBookShelf shelf1 = container.resolve(SingletonBookShelf.class);
        SingletonBookShelf shelf2 = container.resolve(SingletonBookShelf.class);

        assertThat(shelf1.getBook(), is(shelf2.getBook()));
    }
}
