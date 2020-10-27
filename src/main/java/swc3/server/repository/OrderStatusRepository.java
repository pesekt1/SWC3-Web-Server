package swc3.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Byte> {
}
