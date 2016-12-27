package com.sid.resource;

import com.sid.model.Card;
import com.sid.model.Contract;
import com.sid.model.Transaction;
import com.sid.spi.repository.ContractRepository;
import com.sid.spi.repository.TransactionRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

    @GET
    @Path("transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> transactions(@Context TransactionRepository transactionRepository) {
        return transactionRepository.findByCard(card);
    }

    @GET
    @Path("transactions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction transaction(@PathParam("id") String id,
                                    @Context TransactionRepository transactionRepository) {
        return transactionRepository.findById(id);
    }

    @Path("statements")
    public StatementsResource statements() {
        return new StatementsResource(card);
    }
}
