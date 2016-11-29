import jersey.RoutesFeature;
import model.Product;
import model.ProductRepository;
import model.Session;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ProductTest extends JerseyTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    Session session;

    long id = Long.valueOf(1);

    Product product = new Product(1, "data", 30, 500);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("api")
                .packages("model")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(productRepository).to(ProductRepository.class);
                        bind(session).to(Session.class);
                    }
                });
    }

    @Before
    public void before() {
        when(session.isOperator()).thenReturn(true);
        when(productRepository.create(any())).thenReturn(id);
        when(productRepository.findById(id)).thenReturn(product);
    }

    @Test
    public void should_operator_success_to_create_new_product() throws URISyntaxException {
        Response response = target("/products").request().post(Entity.json(product()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "products/1")));
    }


    @Test
    public void should_operator_fail_to_create_a_new_product() throws URISyntaxException {
        when(productRepository.create(any())).thenReturn((long) 0);

        Response response = target("/products").request().post(Entity.json(product()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_user_fail_to_create_a_new_product() throws URISyntaxException {
        when(session.isOperator()).thenReturn(false);

        Response response = target("/products").request().post(Entity.json(product()));

        assertThat(response.getStatus(), is(404));
    }

    private Map<String, Object> product() {
        return new HashMap(){
            {
                put("type", "data");
                put("amount", 500);
                put("price", 30);
            }
        };
    }
}
