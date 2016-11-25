import com.sid.injector.Container;
import com.sid.injector.exceptions.MultiConstructorInjectionException;
import org.junit.Test;
import resources.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ConstructorInjectorTest {

    @Test(expected = MultiConstructorInjectionException.class)
    public void should_throw_exception_if_multiple_inject_annotation() {
        Container container = new Container();
        container.register(TwoConstructorInjectShelf.class);

        container.resolve(TwoConstructorInjectShelf.class);
    }

    @Test
    public void should_inject_default_constructor_instance_if_no_inject_annotation() {
        Container container = new Container();
        container.register(NoConstructorInjectShelf.class);

        BookShelf shelf = container.resolve(NoConstructorInjectShelf.class);

        assertThat(shelf.getBook(), is(nullValue()));
    }

    @Test
    public void should_inject_default_instance_if_no_inject_annotation() {
        Container container = new Container();

        container.register(Shelf.class);

        BookShelf shelf = container.resolve(Shelf.class);

        assertThat(shelf.getBook() instanceof Book, is(true));
    }
}