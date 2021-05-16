package swc3.server.PrimaryDatasource.services.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.repository.InvoiceRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private String errorMessage(long id){
        return "Not found Tutorial with id = " + id;
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
    public void create(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public void delete(int id) {
        if (!invoiceRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        invoiceRepository.deleteById(id);
    }
}
