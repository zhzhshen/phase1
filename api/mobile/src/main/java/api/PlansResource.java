package api;

import model.Plan;
import model.PlanRepository;
import model.Session;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Path("plans")
public class PlansResource {
    @Inject
    PlanRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plan> all() {
        return repository.all();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context Session session) throws URISyntaxException {
        if (session.isOperator()) {
            long planId = repository.create(info);
            if (planId == 0) {
                return Response.status(400).build();
            }
            return Response.created(new URI("plans/" + planId)).build();
        }
        return Response.status(404).build();
    }

    @Path("{plan_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Plan get(@PathParam("plan_id") String id){
        Plan plan = repository.findById(Long.valueOf(id));
        if (plan != null) {
            return plan;
        }
        throw new NotFoundException();
    }
}
