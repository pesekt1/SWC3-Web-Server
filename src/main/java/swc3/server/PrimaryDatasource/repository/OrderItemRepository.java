package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
