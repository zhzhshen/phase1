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
import repository.PurchaseRepository;
import repository.RefillRepository;

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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PurchaseTest extends JerseyTest {
    @Mock
    CardRepository cardRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    RefillRepository refillRepository;

    @Mock
    Session session;

    private String number = "13800000000";

    private Card card = new Card(number, 11.0, 22.2, 33, 1);
    private Plan plan = new Plan(1, 88, 500, 100);
    private Purchase planPurchase = new PlanPurchase(1);
    private Purchase productPurchase = new ProductPurchase(2);

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
                        bind(refillRepository).to(RefillRepository.class);
                    }
                });
    }

    @Before
    public void before() {
        when(cardRepository.findByNumber(eq(number))).thenReturn(card);
        when(planRepository.findById((long)1)).thenReturn(plan);
        when(purchaseRepository.create(any(),any())).thenReturn((long)1);
        when(session.isOperator()).thenReturn(false);
        when(session.validate()).thenReturn(true);
    }

    @Test
    public void should_success_to_create_plan_purchase() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(planPurchase);

        Response response = target("/numbers/" + number + "/plan-purchases").request().post(Entity.json(planPurchase()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "purchases/1")));
    }

    @Test
    public void should_fail_to_create_plan_purchase() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(planPurchase);
        when(purchaseRepository.create(any(),any())).thenReturn((long)0);

        Response response = target("/numbers/" + number + "/plan-purchases").request().post(Entity.json(planPurchase()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_others_fail_to_create_plan_purchase() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(planPurchase);
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/plan-purchases").request().post(Entity.json(planPurchase()));

        assertThat(response.getStatus(), is(404));
    }

    private Map<String, Object> planPurchase() {
        return new HashMap<String, Object>() {{
            put("plan", 1);
            put("price", 30);
            put("date", "2016-10-30");
            put("time", "17:01");
        }};
    }

    @Test
    public void should_success_to_create_product_purchase() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(productPurchase);

        Response response = target("/numbers/" + number + "/product-purchases").request().post(Entity.json(productPurchase()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "purchases/2")));
    }

    @Test
    public void should_fail_to_create_product_purchase() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(productPurchase);
        when(purchaseRepository.create(any(),any())).thenReturn((long)0);

        Response response = target("/numbers/" + number + "/product-purchases").request().post(Entity.json(productPurchase()));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_others_fail_to_create_product_purchase() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(productPurchase);
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/product-purchases").request().post(Entity.json(productPurchase()));

        assertThat(response.getStatus(), is(404));
    }

    private Map<String, Object> productPurchase() {
        return new HashMap<String, Object>() {{
            put("product", 1);
            put("price", 30);
            put("date", "2016-10-30");
            put("time", "17:01");
        }};
    }

    @Test
    public void should_success_to_view_all_purchases_on_a_number() throws URISyntaxException {
        when(purchaseRepository.findByNumber(eq(number))).thenReturn(Arrays.asList(planPurchase, productPurchase));

        Response response = target("/numbers/" + number + "/purchases").request().get();

        assertThat(response.getStatus(), is(200));
        List<Map<String, Object>> results = response.readEntity(List.class);
        assertThat(results.get(0).get("type"), is("plan"));
        assertThat(results.get(1).get("type"), is("product"));
    }

    @Test
    public void should_others_fail_to_view_all_purchases_on_a_number() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/purchases").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_view_a_purchase_on_a_number() throws URISyntaxException {
        when(purchaseRepository.findById(eq((long)1))).thenReturn(productPurchase);

        Response response = target("/numbers/" + number + "/purchases/1").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("type"), is("product"));
    }

    @Test
    public void should_others_fail_to_view_a_purchase_on_a_number() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/purchases/1").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_view_refill_of_a_purchase_on_a_number() throws URISyntaxException {
        Refill refill = new Refill("data", 500);
        when(purchaseRepository.findById(eq((long)1))).thenReturn(productPurchase);
        when(refillRepository.findByPurchase(eq(productPurchase))).thenReturn(refill);

        Response response = target("/numbers/" + number + "/purchases/1/refill").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("type"), is("data"));
    }

    @Test
    public void should_others_fail_to_view_refill_of_a_purchase_on_a_number() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/numbers/" + number + "/purchases/1/refill").request().get();

        assertThat(response.getStatus(), is(404));
    }
}
