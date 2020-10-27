package swc3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.OrderItemNote;

public interface OrderItemNoteRepository extends JpaRepository<OrderItemNote, Integer> {
}
