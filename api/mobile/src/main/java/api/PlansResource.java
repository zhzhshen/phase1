package api;

import model.PlanRepository;
import model.Session;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Path("plans")
public class PlansResource {
    @Inject
    PlanRepository repository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context Session session) throws URISyntaxException {
        if (session.isOperator()) {
            long planId = repository.create(info);
            if (planId == 0) {
                return Response.status(400).build();
            }
            return Response.ok().location(new URI("plans/" + planId)).build();
        }
        return Response.status(404).build();
    }
}
