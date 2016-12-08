package com.sid.mobile.model;

public class Plan {
    private long id;
    private final int price;
    private final int data;
    private final int call;
    private final String name = null;

    public Plan(int id, int price, int data, int call) {
        this.id = id;
        this.price = price;
        this.data = data;
        this.call = call;
    }

    public String getName() {
        return name;
    }

    public int getCall() {
        return call;
    }

    public int getData() {
        return data;
    }

    public int getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }
}
