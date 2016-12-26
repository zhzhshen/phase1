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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (Double.compare(card.balance, balance) != 0) return false;
        if (id != null ? !id.equals(card.id) : card.id != null) return false;
        return number != null ? number.equals(card.number) : card.number == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
