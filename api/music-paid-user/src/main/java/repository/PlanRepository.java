package repository;

import model.Plan;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlanRepository {
    long create(Map<String, Object> info);

    Optional<Plan> findById(long id);

    List<Plan> all();
}
