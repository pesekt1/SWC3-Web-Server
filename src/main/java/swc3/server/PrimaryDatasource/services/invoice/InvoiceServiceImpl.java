package swc3.server.PrimaryDatasource.services.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.models.InvoiceStatus;
import swc3.server.PrimaryDatasource.repository.InvoiceRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private String errorMessage(long id){
        return "Not found invoice with id = " + id;
    }
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getById(int id) {

        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Invoice> getByOrderId(int id) {
        return invoiceRepository.findByOrderId(id);
    }

    @Override
    public List<Invoice> getByStatus(InvoiceStatus status) {
        return invoiceRepository.findByStatus(status);
    }

    @Override
    public void create(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public void delete(int id) {
        if (!invoiceRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        invoiceRepository.deleteById(id);
    }
}
