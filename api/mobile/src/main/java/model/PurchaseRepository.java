package model;

import java.util.Map;

public interface PurchaseRepository {
    long create(Card card, Map<String, Object> info);

    Purchase findById(long id);
}
