package com.sid.model;

public class Repayment {
    private final String id;
    private final String statementId;
    private final double amount;

    public Repayment(String id, String statementId, double amount) {
        this.id = id;
        this.statementId = statementId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getStatementId() {
        return statementId;
    }

    public double getAmount() {
        return amount;
    }
}
