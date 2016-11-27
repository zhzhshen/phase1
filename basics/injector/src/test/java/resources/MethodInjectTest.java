package resources;

import com.sid.injector.Container;
import org.junit.Test;
import resources.field.NoFieldInjectShelf;
import resources.method.NamedMethodParameterShelf;
import resources.method.OneMethodInjectShelf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class MethodInjectTest {

    @Test
    public void should_inject_when_annotated_on_method() {
        Container container = new Container();
        container.register(NoFieldInjectShelf.class);

        BookShelf shelf = container.resolve(OneMethodInjectShelf.class);

        assertThat(shelf.getBook(), is(notNullValue()));
    }

    @Test
    public void should_inject_when_named_annotated_on_method_parameter() {
        Container container = new Container();
        Book theBook = new Book();
        container.register(NoFieldInjectShelf.class).bind(Book.class).annotatedWith("theBook").toInstance(theBook);

        BookShelf shelf = container.resolve(NamedMethodParameterShelf.class);

        assertThat(shelf.getBook(), is(theBook));
    }

}
