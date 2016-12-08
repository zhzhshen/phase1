package com.sid.mobile.repository;

import com.sid.mobile.mapper.PlanMapper;
import com.sid.mobile.model.Plan;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PlanRepository implements com.sid.mobile.spi.repository.PlanRepository {
    @Inject
    PlanMapper mapper;

    @Override
    public long create(Map<String, Object> info) {
        final Plan plan = mapper.findByName(String.valueOf(info.get("name")));
        if (plan != null){
            throw new InvalidParameterException("plan with name " + info.get("name") + " already exist!");
        }

        final String id = UUID.randomUUID().toString();
        info.put("id", id);
        mapper.save(info);
        return mapper.findById(id).getId();
    }

    @Override
    public List<Plan> all() {
        return mapper.all();
    }

    @Override
    public Plan findById(long id) {
        return mapper.findById(String.valueOf(id));
    }
}
