import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsersResourceTest {
    final URI BASE_URI = URI.create("http://localhost:8080/");
    HttpServer server;

    @After
    public void after() {
        server.shutdown();
    }

    @Test
    public void should_success_to_get_all_users () {
        ResourceConfig config = new ResourceConfig(UsersResource.class);
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, true);

        Client client = ClientBuilder.newClient();
        Response res = client.target(BASE_URI + "users").request("application/json").get();

        assertThat(res.getStatus(), is(200));
        assertThat(res.readEntity(String.class), is("{\"firstName\":\"Sid\",\"lastName\":\"Shen\"}"));
    }
}
