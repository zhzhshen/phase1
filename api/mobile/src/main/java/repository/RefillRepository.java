package repository;

import model.Purchase;
import model.Refill;

public interface RefillRepository {
    Refill findByPurchase(Purchase purchase);

    long create(Purchase purchase);
}
