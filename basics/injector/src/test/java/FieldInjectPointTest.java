import com.sid.injector.Container;
import org.junit.Test;
import resources.BookShelf;
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
}
