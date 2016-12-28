import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ServletDispatcherTest {
    final URI BASE_URI = URI.create("http://localhost:1234/");

    @Test
    public void should_return_404_when_no_servlet_dispatcher () throws IOException {
        HttpServer server = new HttpServer();
        final NetworkListener listener =
                new NetworkListener("grizzly",
                        BASE_URI.getHost(),
                        BASE_URI.getPort());
        server.addListener(listener);
        server.start();

        Response response = ClientBuilder.newClient().target(BASE_URI).request().get();

        assertThat(response.getStatus(), is(404));
    }
}
