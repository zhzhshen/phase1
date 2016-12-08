package com.sid.mobile.repository;

import com.sid.mobile.helper.RepositoryTest;
import org.junit.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class PlanRepositoryTest extends RepositoryTest {
    @Inject
    com.sid.mobile.spi.repository.PlanRepository planRepository;

    @Test
    public void should_return_empty_when_call_all () {
        assertThat(planRepository.all().size(), is(0));
    }

    @Test
    public void should_return_null_when_find_by_id () {
        assertThat(planRepository.findById("1"), is(nullValue()));
    }

    @Test
    public void should_save_successfully () {
        String id = planRepository.create(plan());
        assertThat(planRepository.findById(id), is(notNullValue()));
    }

    @Test
    public void should_all_return_size_one () {
        should_save_successfully();
        assertThat(planRepository.all().size(), is(1));
    }

    private Map<String, Object> plan() {
        return new HashMap<String, Object>() {{
            put("name", "88元套餐");
            put("price", 88);
            put("data", 500);
            put("calls", 100);
        }};
    }
}
