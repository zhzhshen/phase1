import model.Plan;
import model.PlanRepository;
import model.Session;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlanTest extends JerseyTest {
    PlanRepository planRepository;
    Session session;

    @Override
    protected Application configure() {
        planRepository = mock(PlanRepository.class);
        session = mock(Session.class);

        return new ResourceConfig().packages("api")
                .packages("model")
                .register(JacksonFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(planRepository).to(PlanRepository.class);
                        bind(session).to(Session.class);
                    }
                });
    }

    @Test
    public void should_operator_success_to_create_a_new_plan() throws URISyntaxException {
        when(session.isOperator()).thenReturn(true);
        when(planRepository.create(any())).thenReturn((long) 1);

        Response response = target("/plans").request().post(Entity.json(plan()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "plans/1")));
    }

    @Test
    public void should_operator_fail_to_create_a_new_plan() throws URISyntaxException {
        when(session.isOperator()).thenReturn(true);
        when(planRepository.create(any())).thenReturn((long) 0);

        Response response = target("/plans").request().post(Entity.json(plan()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_user_fail_to_create_a_new_plan() throws URISyntaxException {
        when(session.isOperator()).thenReturn(false);

        Response response = target("/plans").request().post(Entity.json(plan()));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_all_success_to_view_all_plans() throws URISyntaxException {
        when(session.currentUser()).thenReturn(null);
        when(planRepository.all()).thenReturn(Arrays.asList(new Plan(88, 500, 100)));

        Response response = target("/plans").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(List.class), is(Arrays.asList(plan())));
    }

    @Test
    public void should_all_success_to_view_a_plan() throws URISyntaxException {
        when(session.currentUser()).thenReturn(null);
        when(planRepository.findById((long) 1)).thenReturn(new Plan(88, 500, 100));

        Response response = target("/plans/1").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class), is(plan()));
    }

    @Test
    public void should_all_fail_to_view_a_inexist_plan() throws URISyntaxException {
        when(session.currentUser()).thenReturn(null);
        when(planRepository.findById((long) 1)).thenReturn(null);

        Response response = target("/plans/1").request().get();

        assertThat(response.getStatus(), is(404));
    }


    private Map<String, Object> plan() {
        return new HashMap<String, Object>() {{
            put("price", 88);
            put("data", 500);
            put("call", 100);
        }};
    }
}
