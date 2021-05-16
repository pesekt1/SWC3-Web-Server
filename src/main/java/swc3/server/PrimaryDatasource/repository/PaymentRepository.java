package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerId(int id);
}
