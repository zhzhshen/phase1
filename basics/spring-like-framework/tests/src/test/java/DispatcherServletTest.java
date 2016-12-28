import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DispatcherServletTest {
    private final URI BASE_URI = URI.create("http://localhost:1234/");
    private HttpServer server;

    @Before
    public void before() {
        server = new HttpServer();
        final NetworkListener listener =
                new NetworkListener("grizzly",
                        BASE_URI.getHost(),
                        BASE_URI.getPort());
        server.addListener(listener);

    }

    @Test
    public void should_return_404_when_no_servlet_dispatcher() throws IOException {
        server.start();

        Response response = ClientBuilder.newClient().target(BASE_URI).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_has_servlet_dispatcher_registered() throws IOException {
        WebappContext context = new WebappContext("GrizzlyContext", BASE_URI.getPath());
        ServletRegistration registration = context.addServlet("dispatcherServlet", new DispatcherServlet());
        registration.addMapping("/index");
        context.deploy(server);

        server.start();

        Response response = ClientBuilder.newClient().target(BASE_URI).request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("Welcome!"));
    }

    @After
    public void after() {
        server.shutdown();
    }
}
