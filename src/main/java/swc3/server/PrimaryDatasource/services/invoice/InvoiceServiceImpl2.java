package swc3.server.PrimaryDatasource.services.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.repository.InvoiceRepository;

import java.util.List;

@Service
public class InvoiceServiceImpl2 implements InvoiceService {
    InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl2(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getById(int id) {
        return null;
    }

    @Override
    public List<Invoice> getByOrderId(int id) {
        return null;
    }

    @Override
    public void create(Invoice invoice) {

    }

    @Override
    public void delete(int id) {

    }
}
