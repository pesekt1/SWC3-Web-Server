package swc3.server.PrimaryDatasource.services.invoice;

import swc3.server.PrimaryDatasource.models.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAll();
    Invoice getById(int id);
    List<Invoice> getByOrderId(int id);
    void delete(int id);
}
