package swc3.server.PrimaryDatasource.controller.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import swc3.server.PrimaryDatasource.models.Customer;
import swc3.server.PrimaryDatasource.services.CustomerService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api")
public class CustomersController {
    CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customersSP")
    public ResponseEntity<List<Customer>> getAllCustomersSP2() {
        return customerService.getAllCustomers();
    }

    //stored procedure
    @GetMapping("/customers-stored-procedure")
    public ResponseEntity<List<Customer>> getAllCustomersSP() {
        return customerService.getAllCustomersSP();
    }

    //stored procedure with a parameter
    @GetMapping("/customer-by-id/{id}")
    public ResponseEntity<Customer> getCustomerByIdSP(@PathVariable int id) {
        return customerService.getCustomerByIdSP(id);
    }
}