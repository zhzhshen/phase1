package model;

public class Product {
    private long id;
    private final String type;
    private final int price;
    private final int amount;

    public Product(int id, String type, int price, int amount) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.amount = amount;
    }

    public long getId() {
        return id;
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
