package api;

import model.Product;
import model.ProductRepository;
import model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class ProductsResource {
    private User resourceOwner;
    private List<Product> products;

    public ProductsResource(User resourceOwner, List<Product> products) {
        this.resourceOwner = resourceOwner;
        this.products = products;
    }

    @GET
    public Response get() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context ProductRepository productRepository,
                           @Context SessionImpl session) throws URISyntaxException {
        long id = productRepository.create(info);

        if (productRepository.findProductById(id) != null) {
            return Response.created(new URI("/users/1/products/1")).build();
        }
        return Response.status(400).build();
    }

    @Path("{pid}")
    @GET
    public Response getProduct(@PathParam("pid") long pid,
                               @Context ProductRepository productRepository) {
        if (productRepository.findProductById(pid) != null) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }

    }
}
