package swc3.server.PrimaryDatasource.repository.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.PrimaryDatasource.models.models2.OrderWithIDs;

@RepositoryRestResource(path = "OrdersWithID")
public interface OrderRepository2 extends JpaRepository<OrderWithIDs, Integer> {
}
