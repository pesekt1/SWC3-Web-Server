package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.models.Order;
import swc3.server.repository.CustomerRepository;
import swc3.server.repository.OrderRepository;
import swc3.server.repository.OrderStatusRepository;

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
        int customerId = order.getCustomerByCustomerId().getCustomerId();
        customersRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found customer with id = " + customerId));

        byte orderStatusId = order.getOrderStatusByStatus().getOrderStatusId();
        orderStatusesRepository.findById(orderStatusId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found status with id = " + orderStatusId));

        Order orderNew = ordersRepository.save(order);

        return new ResponseEntity<>(orderNew, HttpStatus.CREATED);
    }
}