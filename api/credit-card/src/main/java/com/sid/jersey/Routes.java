package com.sid.jersey;

import com.sid.model.Card;
import com.sid.model.Repayment;
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
        return URI.create(String.format("%scards/%s/statements/%s", baseUri, statement.getCardId(), statement.getId()));
    }

    public URI repayment(Statement statement, Repayment repayment) {
        return URI.create(String.format("%scards/%s/statements/%s/repayments/%s", baseUri, statement.getCardId(), repayment.getStatementId(), repayment.getId()));
    }
}
