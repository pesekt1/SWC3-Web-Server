package swc3.server.PrimaryDatasource.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.PrimaryDatasource.models.Order;
import swc3.server.PrimaryDatasource.repository.CustomerRepository;
import swc3.server.PrimaryDatasource.repository.OrderRepository;
import swc3.server.PrimaryDatasource.repository.OrderStatusRepository;

@RestController
@RequestMapping("/api")
public class OrdersController3 {
    @Autowired
    OrderRepository ordersRepository;

    @Autowired
    CustomerRepository customersRepository;

    @Autowired
    OrderStatusRepository orderStatusesRepository ;

    //with integrity checks: checking id of customer and status,
    // if not exist - get Not Found http status, with a custom message.
    @PostMapping("/orders3")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        int customerId = order.getCustomerId();
        customersRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found customer with id = " + customerId));

        orderStatusesRepository.findById(order.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Not found status with id = " + order.getStatus()));

        Order orderNew = ordersRepository.save(order);

        return new ResponseEntity<>(orderNew, HttpStatus.CREATED);
    }
}