package com.sid.mobile.repository;

import com.sid.mobile.helper.RepositoryTest;
import com.sid.mobile.helper.TestRunner;
import com.sid.mobile.model.Plan;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class PlanRepositoryTest extends RepositoryTest {
    @Inject
    PlanRepository planRepository;

    @Test
    public void should_ () {
        List<Plan> all = planRepository.all();
        assertThat(all.size(), is(0));
    }

}
