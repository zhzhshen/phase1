package model;

public class DataUsage implements Usage {
    private final String type = "data";
    private final long id;
    private final int amount;
    private String location;

    public DataUsage(long id, int amount, String location) {
        this.id = id;
        this.amount = amount;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }
}
