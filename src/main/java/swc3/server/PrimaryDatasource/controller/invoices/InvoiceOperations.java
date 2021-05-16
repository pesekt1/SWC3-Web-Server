package swc3.server.PrimaryDatasource.controller.invoices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.models.Invoice;
import swc3.server.PrimaryDatasource.models.InvoiceStatus;

import javax.validation.Valid;
import java.util.List;

public interface InvoiceOperations {
    @GetMapping
    List<Invoice> getAll();

    @GetMapping("/{id}")
    Invoice getById(@PathVariable int id);

    @GetMapping("/order/{id}")
    List<Invoice> getByOrderId(@PathVariable int id);

    @GetMapping("/status")
    List<Invoice> getByStatus(@RequestParam(required = true) InvoiceStatus status);

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void update(@Valid @RequestBody Invoice invoice, @PathVariable int id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
