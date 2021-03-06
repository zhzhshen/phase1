package com.sid.mobile.helper;

import com.sid.mobile.jersey.RoutesFeature;
import com.sid.mobile.repository.CardRepository;
import com.sid.mobile.repository.PlanRepository;
import com.sid.mobile.repository.ProductRepository;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Application;

@Ignore
@RunWith(MybatisTestRunner.class)
public class RepositoryTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig().packages("com.sid.mobile")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class);
    }

    @Before
    public void before() {
        ServiceLocator locator = ServiceLocatorUtilities.bind(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(PlanRepository.class).to(com.sid.mobile.spi.repository.PlanRepository.class);
                bind(CardRepository.class).to(com.sid.mobile.spi.repository.CardRepository.class);
                bind(ProductRepository.class).to(com.sid.mobile.spi.repository.ProductRepository.class);
            }
        });
        locator.inject(this);
    }
}