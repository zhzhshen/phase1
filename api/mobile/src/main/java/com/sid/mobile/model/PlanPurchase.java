package com.sid.mobile.model;

import com.sid.mobile.spi.model.Purchase;

public class PlanPurchase implements Purchase {
    private long id;
    private final String type = "plan";

    public PlanPurchase(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
