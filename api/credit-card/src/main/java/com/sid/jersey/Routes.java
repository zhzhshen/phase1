package com.sid.jersey;

import com.sid.model.Card;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

public class Routes {
    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI card(Card card) throws URISyntaxException {
        return URI.create(String.format("%scards/%s", baseUri, card.getId()));
    }
}
