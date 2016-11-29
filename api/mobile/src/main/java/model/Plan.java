package model;

public class Plan {
    private final int price;
    private final int data;
    private final int call;
    private long id;

    public Plan(int id, int price, int data, int call) {
        this.id = id;
        this.price = price;
        this.data = data;
        this.call = call;
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
