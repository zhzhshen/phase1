package com.sid.mobile.model;

import com.sid.mobile.spi.model.Purchase;

public class ProductPurchase implements Purchase {
    private long id;
    private final String type = "product";

    public ProductPurchase(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
