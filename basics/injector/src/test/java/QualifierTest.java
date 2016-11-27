import com.sid.injector.Container;
import org.junit.Test;
import resources.qualifier.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class QualifierTest {
    @Test
    public void should_inject_bind_qualifier_annotated_field() {
        Container container = new Container();
        container.register(LeatherBook.class)
                .qualifier(Leather.class).toInstance(Color.TAN);

        LeatherBook book = container.resolve(LeatherBook.class);

        assertThat(book.getColor(), is(Color.TAN));
    }

    @Test
    public void should_inject_bind_qualifier() {
        Container container = new Container();
        container.register(PaperBook.class)
                .qualifier(Paper.class).toInstance(Color.WHITE);

        PaperBook book = container.resolve(PaperBook.class);

        assertThat(book.getColor(), is(Color.WHITE));
    }

    @Test
    public void should_inject_bind_qualifier_inherited() {
        Container container = new Container();
        container.register(PaperBook.class)
                .qualifier(Paper.class).toInstance(Color.WHITE)
                .bind(String.class).annotatedWith("name").toInstance("Harry Porter");

        PaperBook book = container.resolve(PaperBook.class);

        assertThat(book.getColor(), is(Color.WHITE));
        assertThat(book.getName(), is("Harry Porter"));
    }
}

