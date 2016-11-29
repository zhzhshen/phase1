package repository;

import model.Card;
import model.Purchase;

import java.util.List;
import java.util.Map;

public interface PurchaseRepository {
    long create(Card card, Map<String, Object> info);

    Purchase findById(long id);

    List<Purchase> findByNumber(String id);
}
