package resource;

import com.sid.mobile.jersey.RoutesFeature;
import com.sid.mobile.model.Card;
import com.sid.mobile.spi.repository.CardRepository;
import com.sid.mobile.model.Plan;
import com.sid.mobile.spi.repository.PlanRepository;
import com.sid.mobile.spi.model.Session;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class NumberTest extends JerseyTest{
    @Mock
    CardRepository cardRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    Session session;

    private String number = "13800000000";

    private Card card = new Card(number, 11.0, 22.2, 33, "1");
    private Plan plan = new Plan("1", 88, 500, 100);

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
                    }
                });
    }

    @Before
    public void before() {
        when(cardRepository.findByNumber(eq(number))).thenReturn(card);
        when(planRepository.findById("1")).thenReturn(plan);
        when(session.isOperator()).thenReturn(false);
        when(session.validate()).thenReturn(true);
    }

    @Test
    public void should_success_to_view_summary_of_number() {
        Response response = target("/numbers/" + number).request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("balance"), is(11.0));
    }

    @Test
    public void should_others_fail_to_view_summary_of_number() {
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_others_success_to_view_plan_of_number() {
        Response response = target("/numbers/" + number + "/plan").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("price"), is(88));
    }
}
