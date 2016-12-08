package com.sid.mobile.model;

public class Plan {
    private String id;
    private int price;
    private int data;
    private int call;
    private String name;

    public Plan() {
    }

    public Plan(String id, int price, int data, int call) {
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

    public String getId() {
        return id;
    }
}
