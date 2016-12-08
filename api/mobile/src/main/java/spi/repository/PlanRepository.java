package spi.repository;

import model.Plan;

import java.util.List;
import java.util.Map;

public interface PlanRepository {
    long create(Map<String, Object> info);

    List<Plan> all();

    Plan findById(long id);
}
