package swc3.server.PrimaryDatasource.controller.invoices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.services.invoice.InvoiceService;

import java.util.List;

@RestController
public class InvoicesController implements InvoiceOperations {

    private final InvoiceService invoiceService; //interface

    // @Qualifier - select implementation of the interface
    public InvoicesController(@Qualifier("invoiceServiceImpl") InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceService.getAll();
    }

    @Override
    public Invoice getById(int id) {
        return invoiceService.getById(id);
    }

    @Override
    public List<Invoice> getByOrderId(int id) {
        return invoiceService.getByOrderId(id);
    }

    @Override
    public void delete(int id) {
        invoiceService.delete(id);
    }
}