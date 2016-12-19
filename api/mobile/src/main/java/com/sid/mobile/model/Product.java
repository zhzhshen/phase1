package com.sid.mobile.model;

public class Product {
    private String id;
    private String name;
    private String type;
    private int price;
    private int amount;

    public Product() {
    }

    public Product(String id, String type, int price, int amount) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
