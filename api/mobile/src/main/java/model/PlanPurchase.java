package model;

public class PlanPurchase implements Purchase {
    private long id;

    public PlanPurchase(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
