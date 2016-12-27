package com.sid.resource;

import com.sid.jersey.Routes;
import com.sid.model.Card;
import com.sid.model.Contract;
import com.sid.model.Statement;
import com.sid.model.Transaction;
import com.sid.spi.repository.ContractRepository;
import com.sid.spi.repository.StatementRepository;
import com.sid.spi.repository.TransactionRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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
    public Transaction transactions(@PathParam("id") String id,
                                    @Context TransactionRepository transactionRepository) {
        return transactionRepository.findById(id);
    }

    @GET
    @Path("statements")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Statement> statements(@Context StatementRepository statementRepository) {
        return statementRepository.findByCard(card);
    }

    @POST
    @Path("statements")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStatement(Map<String, Object> info,
                                    @Context Routes routes,
                                    @Context StatementRepository statementRepository) {
        return Response.created(routes.statement(statementRepository.save(info))).build();
    }
}
