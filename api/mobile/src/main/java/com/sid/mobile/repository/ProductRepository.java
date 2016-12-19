package com.sid.mobile.repository;

import com.sid.mobile.mapper.ProductMapper;
import com.sid.mobile.model.Product;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductRepository implements com.sid.mobile.spi.repository.ProductRepository {
    @Inject
    ProductMapper mapper;

    @Override
    public String create(Map<String, Object> info) {
        final Product product = mapper.findByName(String.valueOf(info.get("name")));
        if (product != null){
            throw new InvalidParameterException("product with name " + info.get("name") + " already exist!");
        }

        final String id = UUID.randomUUID().toString();
        info.put("id", id);
        mapper.save(info);
        return mapper.findById(id).getId();
    }

    @Override
    public Product findById(String id) {
        return mapper.findById(String.valueOf(id));
    }

    @Override
    public List<Product> all() {
        return mapper.all();
    }
}
