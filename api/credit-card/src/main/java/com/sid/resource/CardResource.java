package com.sid.resource;

import com.sid.model.Card;
import com.sid.model.Contract;
import com.sid.spi.repository.ContractRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

public class CardResource {
    private Card card;

    public CardResource(Card card) {
        this.card = card;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Card get() {
        return card;
    }

    @GET
    @Path("contract")
    @Produces(MediaType.APPLICATION_JSON)
    public Contract getContract(@Context ContractRepository contractRepository) {
        return contractRepository.findByCard(card);
    }
}
