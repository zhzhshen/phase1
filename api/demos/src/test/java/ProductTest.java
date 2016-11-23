import model.ProductRepository;
import api.SessionImpl;
import model.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductTest extends JerseyTest {
    private User user;
    private SessionImpl session;
    private Order order;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;


    @Override
    protected Application configure() {
        user = mock(User.class);
        session = mock(SessionImpl.class);
        order = mock(Order.class);
        productRepository = mock(ProductRepository.class);
        userRepository = mock(UserRepository.class);
        orderRepository = mock(OrderRepository.class);
        when(userRepository.findUserById(eq(Long.valueOf(1)))).thenReturn(user);
        when(orderRepository.findOrderById(eq(Long.valueOf(1)))).thenReturn(order);
        when(session.currentUser()).thenReturn(user);
        when(session.validate(eq(user))).thenReturn(true);
        return new ResourceConfig().packages("api")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(userRepository).to(UserRepository.class);
                        bind(orderRepository).to(OrderRepository.class);
                        bind(session).to(SessionImpl.class);
                        bind(productRepository).to(ProductRepository.class);
                    }
                });
    }

    @Test
    public void should_get_products_return_200() {

        Response response = target("/users/1/products").request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_post_products_return_400() {
        Response response = target("/users/1/products").request().post(Entity.json(product()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_post_products_return_403() {
        when(session.validate(any())).thenReturn(false);

        Response response = target("/users/1/products").request().post(Entity.json(product()));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_post_products_return_201() throws URISyntaxException {
        Product product = mock(Product.class);
        when(productRepository.create(any())).thenReturn((long)1);
        when(productRepository.findProductById(anyLong())).thenReturn(product);

        Response response = target("/users/1/products").request().post(Entity.json(product()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "users/1/products/1")));
    }

    @Test
    public void should_get_product_return_200() {
        Product product = mock(Product.class);
        when(productRepository.findProductById(1)).thenReturn(product);

        Response response = target("/users/1/products/1").request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_get_product_return_404() {
        when(productRepository.findProductById(1)).thenReturn(null);

        Response response = target("/users/1/products/1").request().get();

        assertThat(response.getStatus(), is(404));
    }

    private Map product() {
        return new HashMap<String, Object>() {{
            put("name", "cookie");
            put("price", "100");
        }};
    }
}
