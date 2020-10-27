package swc3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
