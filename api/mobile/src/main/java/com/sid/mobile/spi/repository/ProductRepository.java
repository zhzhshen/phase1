package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    String create(Map<String, Object> info);

    Product findById(String id);

    List<Product> all();
}
