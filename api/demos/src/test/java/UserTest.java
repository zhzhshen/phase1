import api.SessionImpl;
import model.Order;
import model.OrderRepository;
import model.User;
import model.UserRepository;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest extends JerseyTest {
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private User user;
    private Order order;
    private SessionImpl session;
    private User currentUser;

    @Override
    protected Application configure() {
        userRepository = mock(UserRepository.class);
        orderRepository = mock(OrderRepository.class);
        session = mock(SessionImpl.class);
        currentUser = mock(User.class);
        user = mock(User.class);
        order = mock(Order.class);
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
                    }
                });
    }

    @Test
    public void should_get_user_return_404() {
        Response response = target("/users/2").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_get_user_return_200() {
        Response response = target("/users/1").request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_get_user_return_403() {
        when(session.currentUser()).thenReturn(currentUser);
        when(session.validate(eq(user))).thenReturn(false);

        Response response = target("/users/1").request().get();

        assertThat(response.getStatus(), is(403));
    }

    @Test
    public void should_create_user_return_201() throws URISyntaxException {
        when(userRepository.create(any())).thenReturn((long) 1);

        Response response = target("/users").request().post(Entity.json(user()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "users/1")));
    }

    @Test
    public void should_create_user_return_400() throws URISyntaxException {
        when(userRepository.create(any())).thenReturn((long) 1);

        Response response = target("/users").request().post(Entity.json(emptyPasswordUser()));

        assertThat(response.getStatus(), is(400));
    }


    private Map user() {
        return new HashMap<String, Object>() {{
            put("username", "guest");
            put("password", "1234");
        }};
    }

    private Map emptyPasswordUser() {
        return new HashMap<String, Object>() {{
            put("username", "guest");
            put("password", "");
        }};
    }

}
