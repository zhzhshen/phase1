package model;

import java.util.Date;

public class PlanPrice {
    private long id;
    private final long planId;
    private final double price;
    private final Date date;

    public PlanPrice(long id, long planId, double price, Date date) {
        this.id = id;
        this.planId = planId;
        this.price = price;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public long getPlanId() {
        return planId;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
