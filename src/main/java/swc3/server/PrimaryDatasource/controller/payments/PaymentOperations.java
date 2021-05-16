package swc3.server.PrimaryDatasource.controller.payments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.models.Payment;
import swc3.server.PrimaryDatasource.pojo.PaymentPojo;

import javax.validation.Valid;
import java.util.List;

public interface PaymentOperations {
    @GetMapping
    List<Payment> getAll();

    @GetMapping("/{id}")
    Payment getById(@PathVariable int id);

    @GetMapping("/customer/{id}")
    List<Payment> getByCustomerId(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody PaymentPojo payment);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
