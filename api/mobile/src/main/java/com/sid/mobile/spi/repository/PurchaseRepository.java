package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Card;
import com.sid.mobile.spi.model.Purchase;

import java.util.List;
import java.util.Map;

public interface PurchaseRepository {
    long create(Card card, Map<String, Object> info);

    Purchase findById(long id);

    List<Purchase> findByNumber(String id);
}
