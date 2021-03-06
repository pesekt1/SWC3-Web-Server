package swc3.server.PrimaryDatasource.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Customer;
import swc3.server.PrimaryDatasource.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = new ArrayList<>(customerRepository.findAll());

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> getAllCustomersSP() {
        List<Customer> customers = new ArrayList<>(customerRepository.findAllCustomersSP());

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> getAllCustomersSP2() {
        List<Customer> customers = new ArrayList<>(customerRepository.findAllCustomersSP2());

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public ResponseEntity<Customer> getCustomerByIdSP(int id) {
        Customer customer = customerRepository.findCustomerByIdSP(id);

        if (customer.getCustomerId() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
