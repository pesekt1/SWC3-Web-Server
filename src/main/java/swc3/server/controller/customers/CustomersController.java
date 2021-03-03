package swc3.server.controller.customers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swc3.server.models.Customer;
import swc3.server.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api")
public class CustomersController {

    @Autowired
    CustomerRepository customerRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        customers.addAll(customerRepository.findAll());

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //stored procedure
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers-stored-procedure")
    public ResponseEntity<List<Customer>> getAllCustomersSP() {
        List<Customer> customers = new ArrayList<>();
        customers.addAll(customerRepository.findAllCustomers());

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //stored procedure with a parameter
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customer-by-id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerRepository.findCustomerById(id);

        if (customer.getCustomerId() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}