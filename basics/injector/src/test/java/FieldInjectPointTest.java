import com.sid.injector.Container;
import org.junit.Test;
import resources.Book;
import resources.BookShelf;
import resources.constructor.NamedParameterShelf;
import resources.field.NamedFieldInjectShelf;
import resources.field.NoFieldInjectShelf;
import resources.field.OneFieldInjectShelf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class FieldInjectPointTest {
    @Test
    public void should_not_inject_when_no_inject_annotation_on_fields() {
        Container container = new Container();
        container.register(NoFieldInjectShelf.class);

        BookShelf shelf = container.resolve(NoFieldInjectShelf.class);

        assertThat(shelf.getBook(), is(nullValue()));
    }

    @Test
    public void should_inject_on_field_with_inject_annotation() {
        Container container = new Container();
        container.register(OneFieldInjectShelf.class);

        BookShelf shelf = container.resolve(OneFieldInjectShelf.class);

        assertThat(shelf.getBook(), is(notNullValue()));
    }

    @Test
    public void should_inject_on_field_with_named_annotation() {
        Container container = new Container();
        Book theBook = new Book();
        container.register(NamedFieldInjectShelf.class).bind(Book.class).annotatedWith("theBook").toInstance(theBook);

        BookShelf shelf = container.resolve(NamedFieldInjectShelf.class);

        assertThat(shelf.getBook(), is(theBook));
    }

    @Test
    public void should_nested_named_field_with_named_annotation() {
        Container container = new Container();
        Book theBook = new Book();
        String bookName = "Harry Porter";
        container.register(NamedFieldInjectShelf.class)
                .bind(Book.class).annotatedWith("theBook").toInstance(theBook)
                .bind(String.class).annotatedWith("name").toInstance(bookName);

        BookShelf shelf = container.resolve(NamedParameterShelf.class);

        assertThat(shelf.getBook(), is(theBook));
        assertThat(shelf.getBook().getName(), is(bookName));
    }
}
