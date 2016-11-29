package model;

import java.util.Map;

public interface PlanRepository {
    long create(Map<String, Object> info);
}
