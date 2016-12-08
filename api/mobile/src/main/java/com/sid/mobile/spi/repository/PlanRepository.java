package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Plan;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
import java.util.Map;

@Contract
public interface PlanRepository {
    String create(Map<String, Object> info);

    List<Plan> all();

    Plan findById(String id);
}
