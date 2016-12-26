package com.sid.resource;

import com.sid.jersey.Routes;
import com.sid.model.Card;
import com.sid.spi.repository.CardRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Map;

@Path("cards")
public class CardsResource {
    @Inject
    Routes routes;

    @Inject
    CardRepository repository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info) throws URISyntaxException {
        Card card = repository.create(info);
        if (card == null) {
            return Response.status(400).build();
        }
        return Response.created(routes.card(card)).build();
    }
}
