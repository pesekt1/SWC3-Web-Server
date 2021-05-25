package swc3.server.PrimaryDatasource.services.invoice;

import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.models.InvoiceStatus;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAll();
    Invoice getById(int id);
    List<Invoice> getByOrderId(int id);
    List<Invoice> getByStatus(InvoiceStatus status);
    void checkOverdue();
    void create(Invoice invoice);
    void delete(int id);
}
