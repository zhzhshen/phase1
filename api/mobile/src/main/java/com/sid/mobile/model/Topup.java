package com.sid.mobile.model;

public class Topup {
    private long id;
    private final double amount;

    public Topup(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}
