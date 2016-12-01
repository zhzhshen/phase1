package resource;

import jersey.Routes;
import model.Plan;
import repository.PlanPriceRepository;
import session.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class PlanResource {
    private Plan plan;

    public PlanResource(Plan plan) {
        this.plan = plan;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Plan get() {
        return plan;
    }

    @POST
    @Path("prices")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPrice(Map<String, Object> info,
                                @Context PlanPriceRepository repository,
                                @Context Session session,
                                @Context Routes routes) {
        if (session.isAdmin()) {
            long priceId = repository.create(plan, info);
            if (priceId == 0) {
                return Response.status(400).build();
            }
            return Response.created(routes.planPrice(repository.findById(priceId).get())).build();
        }
        return Response.status(404).build();
    }

}
