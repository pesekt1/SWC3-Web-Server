package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.OrderItemNote;

public interface OrderItemNoteRepository extends JpaRepository<OrderItemNote, Integer> {
}
