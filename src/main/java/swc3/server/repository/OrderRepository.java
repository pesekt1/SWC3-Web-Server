package swc3.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
