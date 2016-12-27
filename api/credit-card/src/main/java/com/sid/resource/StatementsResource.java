package com.sid.resource;

import com.sid.jersey.Routes;
import com.sid.model.Card;
import com.sid.model.Statement;
import com.sid.spi.repository.StatementRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class StatementsResource {
    private Card card;

    public StatementsResource(Card card) {
        this.card = card;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Statement> statements(@Context StatementRepository statementRepository) {
        return statementRepository.findByCard(card);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStatement(Map<String, Object> info,
                                    @Context Routes routes,
                                    @Context StatementRepository statementRepository) {
        return Response.created(routes.statement(statementRepository.save(info))).build();
    }

    @Path("{id}")
    public StatementResource statement(@PathParam("id") String id,
                               @Context StatementRepository statementRepository) {
        return new StatementResource(statementRepository.findById(id));
    }
}
