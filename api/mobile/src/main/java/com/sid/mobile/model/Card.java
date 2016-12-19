package com.sid.mobile.model;

public class Card {
    private String number;
    private double balance;
    private double data;
    private int calls;
    private String planId;

    public Card() {
    }

    public Card(String number, double balance, double data, int calls, String planId) {
        this.number = number;
        this.balance = balance;
        this.data = data;
        this.calls = calls;
        this.planId = planId;
    }

    public double getBalance() {
        return balance;
    }

    public double getData() {
        return data;
    }

    public int getCalls() {
        return calls;
    }

    public String getPlanId() {
        return planId;
    }

    public String getNumber() {
        return number;
    }
}
