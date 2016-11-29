package api;

import jersey.Routes;
import model.Product;
import repository.ProductRepository;
import model.Session;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Path("products")
public class ProductsResource {
    @Inject
    Routes routes;

    @Inject
    ProductRepository repository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context Session session) throws URISyntaxException {
        if (session.isOperator()) {
            long productId = repository.create(info);
            if (productId == 0) {
                return Response.status(400).build();
            }
            return Response.created(routes.product(repository.findById(productId))).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> all() {
        return repository.all();
    }

    @GET
    @Path("{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product get(@PathParam("product_id") String id) {
        Product product = repository.findById(Long.valueOf(id));
        if (product != null) {
            return product;
        }
        throw new NotFoundException();
    }
}
