package com.sid.model;

public class Card {
    private String id;
    private String number;
    private double balance;

    public Card(String id, String number, double balance) {
        this.id = id;
        this.number = number;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }
}
