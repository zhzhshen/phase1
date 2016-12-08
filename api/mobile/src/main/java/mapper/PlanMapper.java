package mapper;

import model.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PlanMapper {
    Plan findByName(@Param("name") String name);

    long save(@Param("info") Map<String, Object> info);

    Plan findById(@Param("id") String id);

    List<Plan> all();
}
