package swc3.server.PrimaryDatasource.nativequeries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.PrimaryDatasource.models.Customer;


import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/extra")
public class CustomersController2 {

    @Autowired
    CustomersRepository2 customersRepository2;

    @Autowired
    CustomCustomerRepository2 customCustomerRepository2;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllOrders() {
        List<Customer> customers = customersRepository2.findAll(Sort.by(Sort.Direction.DESC, "lastName"));
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Transactional(isolation = Isolation.DEFAULT)
    @GetMapping("/customers-update-points")
    public ResponseEntity<Customer> addPoints(
            @RequestParam(defaultValue = "0") int customerId,
            @RequestParam(defaultValue = "0") int points) {
        int numberOfRows = customersRepository2.addCustomerPoints(customerId,points);
        Customer updatedCustomer = customersRepository2.findById(customerId).get();
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @GetMapping("/customers-by-cities")
    public ResponseEntity<List<Customer>> getCustomersByCities(
            @RequestParam(defaultValue = "") Set<String> cities){
        List<Customer> customers = customCustomerRepository2.findCustomersByCities(cities);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            timeout = 60)
    @GetMapping("/insert-customer")
    public ResponseEntity<HttpStatus> insertCustomer(
            @RequestParam(defaultValue = "-") String firstName,
            @RequestParam(defaultValue = "-") String lastName,
            @RequestParam(defaultValue = "-") String address,
            @RequestParam(defaultValue = "-") String state,
            @RequestParam(defaultValue = "-") String city,
            @RequestParam(defaultValue = "0") Integer points
    ){
        customersRepository2.insertCustomer(firstName, lastName, address, state, city, points);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
