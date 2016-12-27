package com.sid.jersey;

import com.sid.model.Card;
import com.sid.model.Statement;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Routes {
    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI card(Card card) {
        return URI.create(String.format("%scards/%s", baseUri, card.getId()));
    }

    public URI statement(Statement statement) {
        return URI.create(String.format("%sstatements/%s", baseUri, statement.getId()));
    }
}
