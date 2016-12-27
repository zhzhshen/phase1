package com.sid.model;

public class Statement {
    private String id;
    private final String cardId;
    private final int month;
    private final int year;
    private final double total;

    public Statement(String id, String cardId, int year, int month, double total) {
        this.cardId = cardId;
        this.id = id;
        this.month = month;
        this.year = year;
        this.total = total;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getTotal() {
        return total;
    }

    public String getId() {
        return id;
    }

    public String getCardId() {
        return cardId;
    }
}
