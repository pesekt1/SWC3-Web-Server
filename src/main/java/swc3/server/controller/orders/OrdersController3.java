package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.model.Order;
import swc3.server.repository.CustomerRepository;
import swc3.server.repository.OrderRepository;
import swc3.server.repository.OrderStatusRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersController3 {
    @Autowired
    OrderRepository ordersRepository;

    @Autowired
    CustomerRepository customersRepository;

    @Autowired
    OrderStatusRepository orderStatusesRepository ;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.addAll(ordersRepository.findAll());

        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

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