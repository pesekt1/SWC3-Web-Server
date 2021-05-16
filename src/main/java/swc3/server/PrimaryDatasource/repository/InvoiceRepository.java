package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.models.InvoiceStatus;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByOrderId(int id);
    List<Invoice> findByStatus(InvoiceStatus status);
}
