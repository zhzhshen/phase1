package com.sid.resource;

import com.sid.model.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
