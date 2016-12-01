package resource;

import jersey.Routes;
import model.Plan;
import repository.PlanRepository;
import session.Session;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("plans")
public class PlansResource {
    @Inject
    Routes routes;

    @Inject
    PlanRepository repository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context Session session) throws URISyntaxException {
        if (session.isAdmin()) {
            long planId = repository.create(info);
            if (planId == 0) {
                return Response.status(400).build();
            }
            return Response.created(routes.plan(repository.findById(planId).get())).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plan> all() {
        return repository.all();
    }

    @GET
    @Path("{plan_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Plan get(@PathParam("plan_id") String id) {
        Optional<Plan> plan = repository.findById(Long.valueOf(id));
        if (plan.isPresent()) {
            return plan.get();
        }
        throw new NotFoundException();
    }
}
