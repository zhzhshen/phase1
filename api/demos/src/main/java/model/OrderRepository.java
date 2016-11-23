package model;

import java.util.List;
import java.util.Map;

public interface OrderRepository {
    List<Order> findOrdersByUser(User currentUser);

    long createOrder(Map<String, Object> info);

    Order findOrderById(long orderId);
}
