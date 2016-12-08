package com.sid.mobile.helper;

import com.sid.mobile.jersey.RoutesFeature;
import com.sid.mobile.repository.PlanRepository;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;

import javax.ws.rs.core.Application;

public class RepositoryTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig().packages("com.sid.mobile")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class);
    }

    @Before
    public void setUp() {
        ServiceLocator locator = ServiceLocatorUtilities.bind(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(PlanRepository.class).to(PlanRepository.class);
            }
        });
        locator.inject(this);
    }
}