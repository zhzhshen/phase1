package com.sid.mobile.resource;

import com.sid.mobile.helper.TestData;
import com.sid.mobile.jersey.RoutesFeature;
import com.sid.mobile.model.Product;
import com.sid.mobile.spi.model.Session;
import com.sid.mobile.spi.repository.ProductRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ProductTest extends JerseyTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    Session session;

    String id = "1";

    Product product = new Product("1", "data", 30, 500);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("com.sid.mobile")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(productRepository).to(ProductRepository.class);
                        bind(session).to(Session.class);
                    }
                });
    }

    @Before
    public void before() {
        when(session.isOperator()).thenReturn(true);
        when(productRepository.create(any())).thenReturn(id);
        when(productRepository.findById(id)).thenReturn(product);
        when(productRepository.all()).thenReturn(Arrays.asList(product));
    }

    @Test
    public void should_operator_success_to_create_new_product() throws URISyntaxException {
        Response response = target("/products").request().post(Entity.json(TestData.PRODUCT));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "products/1")));
    }


    @Test
    public void should_operator_fail_to_create_a_new_product() throws URISyntaxException {
        when(productRepository.create(any())).thenReturn(null);

        Response response = target("/products").request().post(Entity.json(TestData.PRODUCT));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_user_fail_to_create_a_new_product() throws URISyntaxException {
        when(session.isOperator()).thenReturn(false);

        Response response = target("/products").request().post(Entity.json(TestData.PRODUCT));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_all_success_to_view_all_products() throws URISyntaxException {

        Response response = target("/products").request().get();

        assertThat(response.getStatus(), is(200));

        Map result = (Map) response.readEntity(List.class).get(0);
        assertThat(result.get("price"), is(30));
    }

    @Test
    public void should_all_success_to_view_a_product() throws URISyntaxException {
        Response response = target("/products/" + id).request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("price"), is(30));
    }

    @Test
    public void should_all_fail_to_view_a_inexist_product() throws URISyntaxException {
        when(productRepository.findById(id)).thenReturn(null);

        Response response = target("/products/" + id).request().get();

        assertThat(response.getStatus(), is(404));
    }
}
