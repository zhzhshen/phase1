package resources;

import com.sid.injector.Container;
import org.junit.Test;
import resources.method.MultiInjectMethodShelf;
import resources.method.NamedMethodParameterShelf;
import resources.method.OneMethodInjectShelf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class MethodInjectTest {

    @Test
    public void should_inject_when_annotated_on_method() {
        Container container = new Container();
        container.register(OneMethodInjectShelf.class);

        BookShelf shelf = container.resolve(OneMethodInjectShelf.class);

        assertThat(shelf.getBook(), is(notNullValue()));
    }

    @Test
    public void should_inject_when_named_annotated_on_method_parameter() {
        Container container = new Container();
        Book theBook = new Book();
        container.register(NamedMethodParameterShelf.class)
                .bind(Book.class).annotatedWith("theBook").toInstance(theBook);

        BookShelf shelf = container.resolve(NamedMethodParameterShelf.class);

        assertThat(shelf.getBook(), is(theBook));
    }

    @Test
    public void should_inject_when_annotated_on_multi_method() {
        Container container = new Container();
        container.register(MultiInjectMethodShelf.class)
                .bind(String.class).annotatedWith("fiction").toInstance("Fiction")
                .bind(String.class).annotatedWith("name").toInstance("Harry Porter");

        MultiInjectMethodShelf shelf = container.resolve(MultiInjectMethodShelf.class);

        assertThat(shelf.getBook().getName(), is("Harry Porter"));
        assertThat(shelf.getTag(), is("Fiction"));
    }

}
