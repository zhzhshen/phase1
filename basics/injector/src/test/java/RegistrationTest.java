import com.sid.injector.Container;
import com.sid.injector.exceptions.NoRegistrationException;
import org.junit.Test;
import resources.Book;


public class RegistrationTest {

    @Test(expected = NoRegistrationException.class)
    public void should_throw_exception_if_no_registration() {
        Container container = new Container();

        container.resolve(Book.class);
    }

    @Test
    public void should_not_throw_exception_if_has_registration() {
        Container container = new Container();

        container.register(Book.class);

        container.resolve(Book.class);
    }
}
