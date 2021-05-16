package swc3.server.PrimaryDatasource.controller.invoices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.models.Invoice;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/invoices")
public interface InvoiceOperations {
    @GetMapping
    List<Invoice> getAll();

    @GetMapping("/{id}")
    Invoice getById(@PathVariable int id);

    @GetMapping("/order/{id}")
    List<Invoice> getByOrderId(@PathVariable int id);

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    void create(@Valid @RequestBody Course course);
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void update(@Valid @RequestBody Course course, @PathVariable int id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
