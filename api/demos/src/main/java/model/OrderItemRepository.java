package model;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItem> findItemsByOrder(Order order);

    Optional<OrderItem> findItemsById(long id);
}
