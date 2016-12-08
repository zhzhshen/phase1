package com.sid.mobile.resource;

import com.sid.mobile.jersey.RoutesFeature;
import com.sid.mobile.model.Card;
import com.sid.mobile.model.Plan;
import com.sid.mobile.spi.model.Session;
import com.sid.mobile.model.Topup;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.sid.mobile.spi.repository.CardRepository;
import com.sid.mobile.spi.repository.PlanRepository;
import com.sid.mobile.spi.repository.TopupRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class TopupTest extends JerseyTest {
    @Mock
    CardRepository cardRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    TopupRepository topupRepository;

    @Mock
    Session session;

    private String number = "13800000000";

    private Card card = new Card(number, 11.0, 22.2, 33, "1");
    private Plan plan = new Plan("1", 88, 500, 100);
    private Topup topup = new Topup(1, 100);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("com.sid.mobile")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(session).to(Session.class);
                        bind(cardRepository).to(CardRepository.class);
                        bind(planRepository).to(PlanRepository.class);
                        bind(topupRepository).to(TopupRepository.class);
                    }
                });
    }

    @Before
    public void before() {
        when(cardRepository.findByNumber(eq(number))).thenReturn(card);
        when(planRepository.findById("1")).thenReturn(plan);
        when(topupRepository.create(any(), any())).thenReturn("1");
        when(topupRepository.findById("1")).thenReturn(topup);
        when(session.isOperator()).thenReturn(false);
        when(session.validate()).thenReturn(true);
    }

    @Test
    public void should_success_to_create_topup() {
        Response response = target("/numbers/" + number + "/top-ups").request().post(Entity.json(topup()));

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_fail_to_create_topup() {
        when(topupRepository.create(any(), any())).thenReturn(null);

        Response response = target("/numbers/" + number + "/top-ups").request().post(Entity.json(topup()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_others_fail_to_create_topup() {
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/top-ups").request().post(Entity.json(topup()));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_view_all_topups() {
        when(topupRepository.findByNumber(eq(number))).thenReturn(Arrays.asList(topup));

        Response response = target("/numbers/" + number + "/top-ups").request().get();

        assertThat(response.getStatus(), is(200));
        List<Map<String, Object>> results = response.readEntity(List.class);
        assertThat(results.get(0).get("amount"), is(100.0));
    }

    @Test
    public void should_success_to_view_a_topup() {
        Response response = target("/numbers/" + number + "/top-ups/1").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("amount"), is(100.0));

        response = target("/numbers/" + number + "/top-ups/2").request().get();

        assertThat(response.getStatus(), is(404));
    }

    private Map<String, Object> topup() {
        return new HashMap<String, Object>() {{
            put("amount", 100);
            put("number", number);
            put("date", "2016-10-30");
            put("time", "17:01");
        }};
    }
}
