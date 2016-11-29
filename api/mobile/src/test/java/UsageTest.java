import jersey.RoutesFeature;
import model.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.CardRepository;
import repository.PlanRepository;
import repository.UsageRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class UsageTest extends JerseyTest {
    @Mock
    CardRepository cardRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    UsageRepository usageRepository;

    @Mock
    Session session;

    private String number = "13800000000";
    private String toNumber = "18800000000";

    private Card card = new Card(number, 11.0, 22.2, 33, 1);
    private Plan plan = new Plan(1, 88, 500, 100);
    private Usage callUsage = new CallUsage(1, toNumber, "outgoing", 10);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("api")
                .packages("model")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(session).to(Session.class);
                        bind(cardRepository).to(CardRepository.class);
                        bind(planRepository).to(PlanRepository.class);
                        bind(usageRepository).to(UsageRepository.class);
                    }
                });
    }

    @Before
    public void before() {
        when(cardRepository.findByNumber(eq(number))).thenReturn(card);
        when(planRepository.findById((long) 1)).thenReturn(plan);
        when(usageRepository.create(any(), any())).thenReturn((long) 1);
        when(usageRepository.findById((long) 1)).thenReturn(callUsage);
        when(session.isOperator()).thenReturn(false);
        when(session.validate()).thenReturn(true);
    }

    @Test
    public void should_success_to_create_call_usage() throws URISyntaxException {
        Response response = target("/numbers/" + number + "/calls").request().post(Entity.json(call()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "usages/1")));
    }

    @Test
    public void should_fail_to_create_call_usage() throws URISyntaxException {
        when(usageRepository.create(any(), any())).thenReturn((long) 0);

        Response response = target("/numbers/" + number + "/calls").request().post(Entity.json(call()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_others_fail_to_create_call_usage() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/calls").request().post(Entity.json(call()));

        assertThat(response.getStatus(), is(404));
    }

    private Map<String, Object> call() {
        return new HashMap<String, Object>() {{
            put("to", toNumber);
            put("type", "outgoing");
            put("duration", 10);
        }};
    }
}
