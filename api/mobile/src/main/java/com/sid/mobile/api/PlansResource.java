package com.sid.mobile.api;

import com.google.common.base.Strings;
import com.sid.mobile.jersey.Routes;
import com.sid.mobile.model.Plan;
import com.sid.mobile.spi.repository.PlanRepository;
import com.sid.mobile.spi.model.Session;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

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
        if (session.isOperator()) {
            String planId = repository.create(info);
            if (Strings.isNullOrEmpty(planId)) {
                return Response.status(400).build();
            }
            return Response.created(routes.plan(repository.findById(planId))).build();
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
        Plan plan = repository.findById(id);
        if (plan != null) {
            return plan;
        }
        throw new NotFoundException();
    }
}
