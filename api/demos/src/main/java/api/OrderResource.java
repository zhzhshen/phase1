package api;

import model.Order;
import model.OrderItem;
import model.OrderItemRepository;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class OrderResource {
    private Order order;

    public OrderResource(Order order) {
        this.order = order;
    }

    @GET
    public Response get() {
        if (order != null) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("items")
    public Response getItems(@Context OrderItemRepository orderItemRepository) {
        List<OrderItem> orderItems = orderItemRepository.findItemsByOrder(order);
        return Response.ok().build();
    }

    @POST
    @Path("payment")
    public Response createPayment() throws URISyntaxException {
        return Response.created(new URI("/users/1/orders/1/payment")).build();
    }

    @GET
    @Path("payment")
    public Response getPayment() throws URISyntaxException {
        return Response.ok().build();
    }
}
