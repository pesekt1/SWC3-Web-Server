package swc3.server.PrimaryDatasource.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Byte> {
}
