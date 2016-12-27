package com.sid.model;

public class Statement {
    private final int month;
    private final int year;
    private final double total;
    private String id;

    public Statement(String id, int year, int month, double total) {
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
}
