package swc3.server.repository.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.models2.OrderWithIDs;

public interface OrderRepository2 extends JpaRepository<OrderWithIDs, Integer> {
}
