package repository;

import model.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    long create(Map<String, Object> info);

    Product findById(long id);

    List<Product> all();
}
