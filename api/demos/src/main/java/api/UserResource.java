package api;

import model.OrderRepository;
import model.ProductRepository;
import model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class UserResource {

    private User resourceOwner;

    public UserResource(User user) {
        resourceOwner = user;
    }

    @GET
    public Response getUser(@Context SessionImpl session) {
        if (resourceOwner == null) {
            return Response.status(404).build();
        } else if (!session.validate(resourceOwner)) {
            return Response.status(403).build();
        } else {
            return Response.ok().build();
        }
    }

    @Path("orders")
    public OrdersResource getOrders(@Context OrderRepository orderRepository,
                                    @Context SessionImpl session) {
        if (!session.validate(resourceOwner)) {
            return null;
        }
        return new OrdersResource(orderRepository.findOrdersByUser(resourceOwner));
    }

    @Path("products")
    public ProductsResource getProducts(@Context ProductRepository productRepository,
                                   @Context SessionImpl session) {
        if (!session.validate(resourceOwner)) {
            return null;
        }
        return new ProductsResource(resourceOwner, productRepository.findProductsByUser(resourceOwner));
    }
}
