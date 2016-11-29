package model;

public class Card {
    private double balance;
    private double data;
    private int call;

    public Card(double balance, double data, int call) {
        this.balance = balance;
        this.data = data;
        this.call = call;
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
}
