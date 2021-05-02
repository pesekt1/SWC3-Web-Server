package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.ShippedOrderView;

//@RestResource
public interface ShippedOrderViewRepository extends JpaRepository<ShippedOrderView, Integer> {
}
