package com.sid.mobile.repository;

import com.sid.mobile.helper.RepositoryTest;
import com.sid.mobile.helper.TestData;
import com.sid.mobile.model.Plan;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class PlanRepositoryTest extends RepositoryTest {
    @Inject
    com.sid.mobile.spi.repository.PlanRepository planRepository;

    @Test
    public void should_return_size_one_when_call_all () {
        assertThat(planRepository.all().size(), is(1));
    }

    @Test
    public void should_return_null_when_find_by_id () {
        assertThat(planRepository.findById("1"), is(notNullValue()));
        assertThat(planRepository.findById("2"), is(nullValue()));
    }

    @Test
    public void should_save_successfully () {
        String id = planRepository.create(TestData.PLAN);
        Plan plan = planRepository.findById(id);
        assertThat(plan, is(notNullValue()));
        assertThat(plan.getPrice(), is(88));
        assertThat(plan.getData(), is(500));
        assertThat(plan.getCalls(), is(100));
    }

    @Test
    public void should_all_return_size_one () {
        should_save_successfully();
        assertThat(planRepository.all().size(), is(2));
    }

}
