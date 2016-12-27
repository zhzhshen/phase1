package helper;

import com.sid.jersey.RoutesFeature;
import com.sid.repository.CardRepository;
import com.sid.session.Session;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Application;

@Ignore
@RunWith(MyBatisTestRunner.class)
public class RepositoryTest extends JerseyTest {
    @Mock
    protected Session session;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("com.sid")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(session).to(Session.class);
                    }
                });
    }

    @Before
    public void before() {
        ServiceLocator locator = ServiceLocatorUtilities.bind(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(CardRepository.class).to(com.sid.spi.repository.CardRepository.class);
            }
        });
        locator.inject(this);
    }
}
