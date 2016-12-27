package repository;

import com.sid.model.Card;
import com.sid.spi.model.User;
import com.sid.spi.repository.CardRepository;
import helper.RepositoryTest;
import helper.TestData;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardRepositoryTest extends RepositoryTest {
    @Inject
    private CardRepository cardRepository;

    @Test
    public void should_fail_to_find_card_by_inexist_id () {
        Card card = cardRepository.findById("0");

        assertThat(card, nullValue());
    }

    @Test
    public void should_success_to_find_card_by_id () {
        Card card = cardRepository.findById("1");

        assertThat(card.getNumber(), is("1234567812345678"));
        assertThat(card.getBalance(), is(0.0));
    }

    @Test
    public void should_fail_to_find_card_by_user_if_has_no_one () {
        User user = mock(User.class);
        when(user.getId()).thenReturn("2");

        assertThat(cardRepository.findByUser(user).size(), is(0));
    }

    @Test
    public void should_success_to_find_card_by_user_id () {
        User user = mock(User.class);
        when(user.getId()).thenReturn("1");
        Card card = cardRepository.findByUser(user).get(0);

        assertThat(card.getNumber(), is("1234567812345678"));
        assertThat(card.getBalance(), is(0.0));
    }

    @Test
    public void should_success_to_save_new_card () {
        User user = mock(User.class);
        when(user.getId()).thenReturn("1");
        when(session.currentUser()).thenReturn(user);

        Card card = cardRepository.save(TestData.CARD, user);

        assertThat(cardRepository.findById(card.getId()), notNullValue());
    }

}
