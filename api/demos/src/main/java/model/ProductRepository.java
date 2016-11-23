package model;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    List<Product> findProductsByUser(User user);

    long create(Map<String, Object> info);

    Product findProductById(long id);
}
