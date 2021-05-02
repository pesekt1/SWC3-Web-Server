package swc3.server.PrimaryDatasource.repository.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.PrimaryDatasource.models.models2.OrderItem2;

@RepositoryRestResource(path = "OrderItems2")
public interface OrderItemRepository2 extends JpaRepository<OrderItem2, Integer> {
}
