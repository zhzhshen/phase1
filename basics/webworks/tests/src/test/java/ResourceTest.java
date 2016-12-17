import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResourceTest {
    final URI BASE_URI = URI.create("http://localhost:8080/");
    HttpServer server;

    @After
    public void after() {
        server.shutdown();
    }

    @Test
    public void should_return_404_when_no_handler () throws IOException {
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, true);

        Client client = ClientBuilder.newClient();
        Response res = client.target(BASE_URI).request("text/plain").get();

        assertThat(res.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_has_handler () {
        ResourceConfig config = new ResourceConfig(HelloWorldResource.class);
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, true);

        Client client = ClientBuilder.newClient();
        Response res = client.target(BASE_URI + "hello").request("text/plain").get();

        assertThat(res.getStatus(), is(200));
        assertThat(res.readEntity(String.class), is("Hello World!"));
    }
}
