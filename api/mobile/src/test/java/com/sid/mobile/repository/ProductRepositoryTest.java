package com.sid.mobile.repository;

import com.sid.mobile.helper.RepositoryTest;
import com.sid.mobile.helper.TestData;
import com.sid.mobile.model.Product;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class ProductRepositoryTest extends RepositoryTest {
    @Inject
    com.sid.mobile.spi.repository.ProductRepository productRepository;

    @Test
    public void should_return_size_one_when_call_all () {
        assertThat(productRepository.all().size(), is(1));
    }

    @Test
    public void should_return_null_when_find_by_id () {
        assertThat(productRepository.findById("1"), is(notNullValue()));
        assertThat(productRepository.findById("2"), is(nullValue()));
    }

    @Test
    public void should_save_successfully () {
        String id = productRepository.create(TestData.PRODUCT);
        Product product = productRepository.findById(id);
        assertThat(product, is(notNullValue()));
        assertThat(product.getType(), is("data"));
        assertThat(product.getAmount(), is(500));
        assertThat(product.getPrice(), is(30));
    }

    @Test
    public void should_all_return_size_one () {
        should_save_successfully();
        assertThat(productRepository.all().size(), is(2));
    }
}
