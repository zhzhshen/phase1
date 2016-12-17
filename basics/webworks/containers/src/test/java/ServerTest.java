import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ServerTest {

    @Test
    public void should_success_to_create_default_server () {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(true);

        NetworkListener listener = server.getListener("grizzly");
        assertThat(listener, notNullValue());
        assertThat(listener.getPort(), is(8080));
        assertThat(listener.getHost(), is("0.0.0.0"));
    }
    
    @Test
    public void should_success_to_create_server () {
        final URI BASE_URI = URI.create("http://localhost:1234/");

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, true);

        NetworkListener listener = server.getListener("grizzly");
        assertThat(listener, notNullValue());
        assertThat(listener.getPort(), is(1234));
        assertThat(listener.getHost(), is("localhost"));
    }
}
