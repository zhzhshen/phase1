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

public class PlanPurchaseTest extends JerseyTest {
    @Mock
    CardRepository cardRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    Session session;

    private String number = "13800000000";

    private Card card = new Card(11.0, 22.2, 33, 1);
    private Plan plan = new Plan(1, 88, 500, 100);
    private Purchase purchase = new PlanPurchase(1);

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
                        bind(purchaseRepository).to(PurchaseRepository.class);
                    }
                });
    }

    @Before
    public void before() {
        when(cardRepository.findByNumber(eq(number))).thenReturn(card);
        when(planRepository.findById((long)1)).thenReturn(plan);
        when(purchaseRepository.create(any(),any())).thenReturn((long)1);
        when(purchaseRepository.findById(eq((long)1))).thenReturn(purchase);
        when(session.isOperator()).thenReturn(false);
        when(session.validate()).thenReturn(true);
    }

    @Test
    public void should_success_to_create_plan_purchase() throws URISyntaxException {
        Response response = target("/numbers/" + number + "/plan-purchases").request().post(Entity.json(planPurchase()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "purchases/1")));
    }

    private Map<String, Object> planPurchase() {
        return new HashMap<String, Object>() {{
            put("plan", 1);
            put("date", "2016-10-30");
            put("time", "17:01");
        }};
    }
}
