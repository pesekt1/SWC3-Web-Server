package swc3.server.controller.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import swc3.server.models.Customer;
import swc3.server.services.CustomerService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api")
public class CustomersController {
    CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //stored procedure
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers-stored-procedure")
    public ResponseEntity<List<Customer>> getAllCustomersSP() {
        return customerService.getAllCustomersSP();
    }

    //stored procedure with a parameter
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customer-by-id/{id}")
    public ResponseEntity<Customer> getCustomerByIdSP(@PathVariable int id) {
        return customerService.getCustomerByIdSP(id);
    }
}