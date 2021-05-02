package swc3.server.PrimaryDatasource.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
