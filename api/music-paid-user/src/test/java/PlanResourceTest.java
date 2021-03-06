import jersey.RoutesFeature;
import model.Plan;
import model.PlanPrice;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.PlanPriceRepository;
import repository.PlanRepository;
import session.Session;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PlanResourceTest extends JerseyTest{
    @Mock
    PlanRepository planRepository;

    @Mock
    PlanPriceRepository planPriceRepository;

    @Mock
    Session session;

    private Plan plan = new Plan(1, "6 month plan", 6, 500);
    private PlanPrice planPrice = new PlanPrice(1, 1, 100, new Date());

    long id = Long.valueOf(1);
    long priceId = Long.valueOf(1);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("resource")
                .packages("model")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(planRepository).to(PlanRepository.class);
                        bind(planPriceRepository).to(PlanPriceRepository.class);
                        bind(session).to(Session.class);
                    }
                });
    }

    @Before
    public void before() {
        when(session.isAdmin()).thenReturn(true);
        when(planRepository.create(any())).thenReturn(id);
        when(planRepository.findById(eq(id))).thenReturn(Optional.of(plan));
        when(planRepository.all()).thenReturn(Arrays.asList(plan));
        when(planPriceRepository.create(any(), any())).thenReturn(priceId);
        when(planPriceRepository.findById(eq(priceId))).thenReturn(Optional.of(planPrice));
    }

    @Test
    public void should_admin_success_to_create_a_new_plan() throws URISyntaxException {
        Response response = target("/plans").request().post(Entity.json(plan()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "plans/" + id)));
    }

    @Test
    public void should_admin_fail_to_create_a_new_plan() throws URISyntaxException {
        when(planRepository.create(any())).thenReturn((long) 0);
        Response response = target("/plans").request().post(Entity.json(plan()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_others_fail_to_create_a_new_plan() throws URISyntaxException {
        when(session.isAdmin()).thenReturn(false);
        Response response = target("/plans").request().post(Entity.json(plan()));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_all_success_to_view_all_plans() throws URISyntaxException {
        Response response = target("/plans").request().get();

        assertThat(response.getStatus(), is(200));

        Map result = (Map) response.readEntity(List.class).get(0);
        assertThat(result.get("name"), is("6 month plan"));
        assertThat(result.get("duration"), is(6));
        assertThat(result.get("quota"), is(500));
    }

    @Test
    public void should_all_success_to_view_a_plan() throws URISyntaxException {
        Response response = target("/plans/" + id).request().get();

        assertThat(response.getStatus(), is(200));
        Map result = response.readEntity(Map.class);
        assertThat(result.get("name"), is("6 month plan"));
        assertThat(result.get("duration"), is(6));
        assertThat(result.get("quota"), is(500));
    }

    @Test
    public void should_all_fail_to_view_a_inexist_plan() throws URISyntaxException {
        when(planRepository.findById(id)).thenReturn(Optional.empty());

        Response response = target("/plans/" + id).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_admin_success_to_create_price_for_a_plan() throws URISyntaxException {
        Response response = target("/plans/" + id + "/prices").request().post(Entity.json(planPrice()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "prices/" + priceId)));
    }

    private Map<String, Object> planPrice() {
        return new HashMap<String, Object>() {{
            put("price", 100);
            put("date", new Date());
        }};
    }

    private Map<String, Object> plan() {
        return new HashMap<String, Object>() {{
            put("name", "6 month plan");
            put("duration", 6);
            put("quota", 500);
        }};
    }
}
