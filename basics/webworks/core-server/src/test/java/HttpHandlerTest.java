import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HttpHandlerTest {
    final URI BASE_URI = URI.create("http://localhost:8080/");
    HttpServer server;

    @Before
    public void before() {
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, true);
    }

    @After
    public void after() {
        server.shutdown();
    }

    @Test
    public void should_return_404_when_no_handler () {
        Client client = ClientBuilder.newClient();
        Response res = client.target(BASE_URI).request("text/plain").get();

        assertThat(res.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_has_handler () {
        String result = "Hello World!";
        server.getServerConfiguration().addHttpHandler(new HttpHandler() {
            @Override
            public void service(Request request, org.glassfish.grizzly.http.server.Response response) throws Exception {
                response.setContentType("text/plain");
                response.setContentLength(result.length());
                response.getWriter().write(result);
            }
        }, "/");

        Client client = ClientBuilder.newClient();
        Response res = client.target(BASE_URI).request("text/plain").get();

        assertThat(res.getStatus(), is(200));
        assertThat(res.readEntity(String.class), is(result));
    }
}
