package com.sid.resource;

import com.sid.jersey.Routes;
import com.sid.model.Repayment;
import com.sid.model.Statement;
import com.sid.spi.repository.RepaymentRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class StatementResource {
    private Statement statement;

    public StatementResource(Statement statement) {
        this.statement = statement;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Statement statement() {
        return statement;
    }

    @GET
    @Path("repayments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Repayment> repayments(@Context RepaymentRepository repaymentRepository) {
        return repaymentRepository.findByStatement(statement);
    }

    @POST
    @Path("repayments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRepayment(Map<String, Object> info,
                                    @Context Routes routes,
                                    @Context RepaymentRepository repaymentRepository) {
        Repayment repayment = repaymentRepository.save(info);
        return Response.created(routes.repayment(statement, repayment)).build();
    }

    @GET
    @Path("repayments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Repayment repayments(@PathParam("id") String id,
                                @Context RepaymentRepository repaymentRepository) {
        return repaymentRepository.findById(id);
    }
}
