import jersey.RoutesFeature;
import model.Card;
import model.CardRepository;
import model.Session;
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
    Session session;

    private String number = "13800000000";

    private Card card = new Card(11, 22.2, 33);

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
                    }
                });
    }

    @Before
    public void before() {
        when(cardRepository.findByNumber(eq(number))).thenReturn(card);
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
}
