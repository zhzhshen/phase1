package resource;

import com.sid.jersey.RoutesFeature;
import com.sid.model.Card;
import com.sid.spi.repository.CardRepository;
import helper.TestData;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class CardResourceTest extends JerseyTest {
    @Mock
    CardRepository cardRepository;

    Card card = new Card("1","1234567812345678", 0);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("com.sid")
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(cardRepository).to(CardRepository.class);
                    }
                });
    }

    @Test
    public void should_fail_to_create_a_new_card() throws URISyntaxException {
        when(cardRepository.create(any())).thenReturn(null);

        Response response = target("/cards").request().post(Entity.json((Map<String,Object>) TestData.CARD));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_fail_to_create_card () throws URISyntaxException {
        when(cardRepository.create(any())).thenReturn(card);

        Response response = target("/cards").request().post(Entity.json((Map<String,Object>) TestData.CARD));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "cards/" + 1)));

    }
}
