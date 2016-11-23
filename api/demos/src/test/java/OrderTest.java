import api.SessionImpl;
import model.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
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

public class OrderTest extends JerseyTest{
    private User user;
    private SessionImpl session;
    private Order order;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private User currentUser;

    @Override
    protected Application configure() {
        currentUser = mock(User.class);
        user = mock(User.class);
        session = mock(SessionImpl.class);
        order = mock(Order.class);
        userRepository = mock(UserRepository.class);
        orderRepository = mock(OrderRepository.class);
        orderItemRepository = mock(OrderItemRepository.class);
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
                        bind(orderItemRepository).to(OrderItemRepository.class);
                        bind(session).to(SessionImpl.class);
                    }
                });
    }

    @Test
    public void should_get_orders_return_200() {
        Response response = target("/users/1/orders").request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_get_orders_return_404() {
        when(session.currentUser()).thenReturn(currentUser);
        when(session.validate(eq(user))).thenReturn(false);

        Response response = target("/users/1/orders").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_create_order_return_400() {
        when(orderRepository.createOrder(any())).thenReturn((long) 1);
        when(orderRepository.findOrderById(eq((long) 1))).thenReturn(null);
        Response response = target("/users/1/orders").request().post(Entity.json(order()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_create_order_return_201() throws URISyntaxException {
        when(orderRepository.createOrder(any())).thenReturn((long) 1);
        when(orderRepository.findOrderById(eq((long) 1))).thenReturn(order);
        Response response = target("/users/1/orders").request().post(Entity.json(order()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "users/1/orders/1")));
    }

    @Test
    public void should_get_order_return_404() {
        Response response = target("/users/1/orders/2").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_get_order_return_200() {
        when(orderRepository.findOrderById(eq((long)1))).thenReturn(order);

        Response response = target("/users/1/orders/1").request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_get_order_items_return_200() {
        when(orderItemRepository.findItemsByOrder(order)).thenReturn(null);

        Response response = target("/users/1/orders/1/items").request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Ignore
    @Test
    public void should_get_order_item_return_404() {
        when(orderRepository.findOrderById(eq((long)1))).thenReturn(order);

        Response response = target("/users/1/orders/1/items/1").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_create_order_payment_return_201() throws URISyntaxException {
        when(orderRepository.findOrderById(eq((long)1))).thenReturn(order);

        Response response = target("/users/1/orders/1/payment").request().post(Entity.json(payment()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "users/1/orders/1/payment")));
    }

    @Test
    public void should_create_order_payment_return_404() {
        when(orderRepository.findOrderById(eq((long)1))).thenReturn(order);
        when(session.currentUser()).thenReturn(currentUser);
        when(session.validate(eq(user))).thenReturn(false);

        Response response = target("/users/1/orders/1/payment").request().post(Entity.json(payment()));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_get_order_payment_return_200() {
        when(orderRepository.findOrderById(eq((long)1))).thenReturn(order);

        Response response = target("/users/1/orders/1/payment").request().get();

        assertThat(response.getStatus(), is(200));
    }

    private Map order() {
        return new HashMap<String, Object>() {{
            put("quantity", "1");
        }};
    }

    private Map payment() {
        return new HashMap<String, Object>() {{
            put("account", "guest");
        }};
    }

}
