package com.sid.resource;

import com.sid.jersey.Routes;
import com.sid.model.Card;
import com.sid.session.Session;
import com.sid.spi.repository.CardRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Path("cards")
public class CardsResource {
    @Inject
    Routes routes;

    @Inject
    CardRepository repository;

    @Inject
    Session session;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info) throws URISyntaxException {
        Card card = repository.create(info);
        if (card == null) {
            return Response.status(400).build();
        }
        return Response.created(routes.card(card)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> all() {
        if (session.validate()) {
            return repository.findByUser(session.currentUser());
        }
        throw new NotFoundException();
    }

    @Path("{id}")
    public CardResource get(@PathParam("id") String id) {
        if (session.validate()) {
            return new CardResource(repository.findById(id));
        }
        throw new NotFoundException();
    }
}
