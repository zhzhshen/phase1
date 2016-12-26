package resource;

import com.sid.jersey.RoutesFeature;
import com.sid.model.Card;
import com.sid.model.Contract;
import com.sid.model.Transaction;
import com.sid.session.Session;
import com.sid.spi.repository.CardRepository;
import com.sid.spi.repository.ContractRepository;
import com.sid.spi.repository.TransactionRepository;
import helper.TestData;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class CardResourceTest extends JerseyTest {
    @Mock
    Session session;

    @Mock
    CardRepository cardRepository;

    @Mock
    ContractRepository contractRepository;

    @Mock
    TransactionRepository transactionRepository;

    Card card = new Card("1","1234567812345678", 0);
    Contract contract = new Contract("contract of card 1");
    Transaction transaction = new Transaction(100);

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().packages("com.sid")
                .register(JacksonFeature.class)
                .register(RoutesFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(session).to(Session.class);
                        bind(cardRepository).to(CardRepository.class);
                        bind(contractRepository).to(ContractRepository.class);
                        bind(transactionRepository).to(TransactionRepository.class);
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
    public void should_success_to_create_card () throws URISyntaxException {
        when(cardRepository.create(any())).thenReturn(card);

        Response response = target("/cards").request().post(Entity.json((Map<String,Object>) TestData.CARD));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(new URI(getBaseUri() + "cards/" + 1)));
    }

    @Test
    public void should_fail_to_get_all_card() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/cards").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_get_all_card() throws URISyntaxException {
        when(session.validate()).thenReturn(true);
        when(cardRepository.findByUser(any())).thenReturn(Collections.singletonList(card));

        Response response = target("/cards").request().get();

        assertThat(response.getStatus(), is(200));
        Map<String, Object> cardInfo = (Map<String, Object>) response.readEntity(List.class).get(0);
        assertThat(cardInfo.get("id"), is("1"));
        assertThat(cardInfo.get("number"), is("1234567812345678"));
        assertThat(cardInfo.get("balance"), is(0.0));
    }

    @Test
    public void should_fail_to_get_a_specific_card() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/cards/1").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_get_a_specific_card() throws URISyntaxException {
        when(session.validate()).thenReturn(true);
        when(cardRepository.findById(eq("1"))).thenReturn(card);

        Response response = target("/cards/1").request().get();

        assertThat(response.getStatus(), is(200));
        Map<String, Object> cardInfo = (Map<String, Object>) response.readEntity(Map.class);
        assertThat(cardInfo.get("id"), is("1"));
        assertThat(cardInfo.get("number"), is("1234567812345678"));
        assertThat(cardInfo.get("balance"), is(0.0));
    }




    @Test
    public void should_fail_to_view_contract_of_a_card() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/cards/1/contract").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_view_contract_of_a_card() throws URISyntaxException {
        when(session.validate()).thenReturn(true);
        when(cardRepository.findById(eq("1"))).thenReturn(card);
        when(contractRepository.findByCard(any())).thenReturn(contract);

        Response response = target("/cards/1/contract").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Map.class).get("description"), is("contract of card 1"));
    }

    @Test
    public void should_fail_to_view_transactions_of_a_card() throws URISyntaxException {
        when(session.validate()).thenReturn(false);

        Response response = target("/cards/1/transactions").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_success_to_view_transactions_of_a_card() throws URISyntaxException {
        when(session.validate()).thenReturn(true);
        when(cardRepository.findById(eq("1"))).thenReturn(card);
        when(transactionRepository.findByCard(card)).thenReturn(Collections.singletonList(transaction));

        Response response = target("/cards/1/transactions").request().get();

        assertThat(response.getStatus(), is(200));
        Map<String, Object> transactionInfo = (Map<String, Object>) response.readEntity(List.class).get(0);
        assertThat(transactionInfo.get("amount"), is(100.0));
    }
}
