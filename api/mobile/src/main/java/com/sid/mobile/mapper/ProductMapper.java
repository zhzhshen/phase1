package com.sid.mobile.mapper;

import com.sid.mobile.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    Product findByName(@Param("name") String name);

    long save(@Param("info") Map<String, Object> info);

    Product findById(@Param("id") String id);

    List<Product> all();
}
