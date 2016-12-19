package com.sid.mobile.model;

public class Plan {
    private String id;
    private int price;
    private int data;
    private int calls;
    private String name;

    public Plan() {
    }

    public Plan(String id, int price, int data, int calls) {
        this.id = id;
        this.price = price;
        this.data = data;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public int getCalls() {
        return calls;
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
