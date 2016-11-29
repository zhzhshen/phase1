package model;

import java.util.Map;

public interface ProductRepository {
    long create(Map<String, Object> info);

    Product findById(long id);
}
