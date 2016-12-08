package com.sid.mobile.spi.repository;

import com.sid.mobile.spi.model.Purchase;
import com.sid.mobile.model.Refill;

public interface RefillRepository {
    Refill findByPurchase(Purchase purchase);

    long create(Purchase purchase);
}
