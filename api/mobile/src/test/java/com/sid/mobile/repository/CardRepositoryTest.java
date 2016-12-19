package com.sid.mobile.repository;

import com.sid.mobile.helper.RepositoryTest;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class CardRepositoryTest extends RepositoryTest {
    @Inject
    com.sid.mobile.spi.repository.CardRepository cardRepository;

    @Test
    public void should_return_null_when_find_by_inexist_number () {
        assertThat(cardRepository.findByNumber("13800000001"), nullValue());
    }

    @Test
    public void should_return_card_when_find_by_existing_number () {
        assertThat(cardRepository.findByNumber("13800000000"), notNullValue());
    }
}
