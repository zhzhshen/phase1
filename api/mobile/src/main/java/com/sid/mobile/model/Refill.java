package com.sid.mobile.model;

public class Refill {
    private final String type;
    private final double amount;

    public Refill(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
