package model;

public class Card {
    private String number;
    private double balance;
    private double data;
    private int call;
    private long planId;

    public Card(String number, double balance, double data, int call, long planId) {
        this.number = number;
        this.balance = balance;
        this.data = data;
        this.call = call;
        this.planId = planId;
    }

    public double getBalance() {
        return balance;
    }

    public double getData() {
        return data;
    }

    public int getCall() {
        return call;
    }

    public long getPlanId() {
        return planId;
    }

    public String getNumber() {
        return number;
    }
}
