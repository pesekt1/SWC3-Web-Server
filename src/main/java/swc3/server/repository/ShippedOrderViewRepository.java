package swc3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.ShippedOrderView;

//@RestResource
public interface ShippedOrderViewRepository extends JpaRepository<ShippedOrderView, Integer> {
}
