package api;

import model.Order;
import model.OrderRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class OrdersResource {
    private List<Order> orders;

    public OrdersResource(List<Order> orders) {
        this.orders = orders;
    }

    @GET
    public Response getOrders() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context OrderRepository orderRepository) throws URISyntaxException {
        long orderId = orderRepository.createOrder(info);

        if (orderRepository.findOrderById(orderId) != null) {
            return Response.created(new URI("/users/1/orders/1")).build();
        } else {
            return Response.status(400).build();
        }
    }

    @Path("{oid}")
    public OrderResource getOrder(@PathParam("oid") long oid,
                             @Context OrderRepository orderRepository) {
        return new OrderResource(orderRepository.findOrderById(oid));
    }
}
