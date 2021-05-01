package swc3.server.repository.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.models.models2.OrderItemNote2;

@RepositoryRestResource(path = "OrderItemNotes2")
public interface OrderItemNoteRepository2 extends JpaRepository<OrderItemNote2, Integer> {
}
