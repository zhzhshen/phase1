package repository;

import model.Plan;
import model.PlanPrice;

import java.util.Map;
import java.util.Optional;

public interface PlanPriceRepository {
    long create(Plan plan, Map<String, Object> info);

    Optional<PlanPrice> findById(long id);
}

