import com.sid.injector.Container;
import com.sid.injector.exceptions.BindingException;
import org.junit.Test;
import resources.Book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NamedBindingTest {

    @Test
    public void should_inject_default_instance_if_no_binding() {
        Container container = new Container();
        container.register(Book.class);

        Book book = container.resolve(Book.class);
        assertThat(book instanceof Book, is(true));
    }

    @Test
    public void should_inject_specified_instance_if_with_binding() {
        Container container = new Container();
        Book theBook = new Book();
        container.bind(Book.class).annotatedWith("the book").toInstance(theBook);

        Book book = container.resolveBinding("the book", null);

        assertThat(book == theBook, is(true));
    }

    @Test(expected = BindingException.class)
    public void should_throw_excaption_if_bind_with_instance_of_other_class() {
        Container container = new Container();

        container.bind(Book.class).annotatedWith("the book").toInstance("the book");
    }
}
