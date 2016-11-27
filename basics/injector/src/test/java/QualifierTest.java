import com.sid.injector.Container;
import org.junit.Test;
import resources.qualifier.Book;
import resources.qualifier.Leather;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static resources.qualifier.Leather.*;

public class QualifierTest {
    @Test
    public void should_inject_bind_qualifier_annotated_field() {
        Container container = new Container();
        container.register(Book.class).qualifier(Leather.class).toInstance(Color.TAN);

        Book book = container.resolve(Book.class);

        assertThat(book.getColor(), is(Color.TAN));
    }
}

