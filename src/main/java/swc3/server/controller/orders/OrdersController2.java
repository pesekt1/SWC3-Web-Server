package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.model.Customer;
import swc3.server.model.Order;
import swc3.server.model.OrderStatus;
import swc3.server.model.Tutorial;
import swc3.server.repository.CustomerRepository;
import swc3.server.repository.OrderRepository;
import swc3.server.repository.OrderStatusRepository;

import java.util.Optional;

//if we needed to fill the missing fields for customer and order status:
//we query the whole Customer object
//and OrderStatus object
//and we put all the fields in the Order object
// - even though we do not need it because they are not part of Order table...

@RestController
@RequestMapping("/api")
public class OrdersController2 {
    @Autowired
    OrderRepository ordersRepository;

    @Autowired
    CustomerRepository customersRepository;

    @Autowired
    OrderStatusRepository orderStatusesRepository ;

    //with integrity checks: checking id of customer and status,
    // if not exist - get Not Found http status, with a custom message.
    @PostMapping("/orders2")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        int customerId = order.getCustomerByCustomerId().getCustomerId();
        Customer customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found customer with id = " + customerId));

        byte orderStatusId = order.getOrderStatusByStatus().getOrderStatusId();
        OrderStatus orderStatus = orderStatusesRepository.findById(orderStatusId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found status with id = " + orderStatusId));

        Order orderNew = new Order();
        orderNew.setOrderDate(order.getOrderDate());
        orderNew.setComments(order.getComments());
        orderNew.setShippedDate(order.getShippedDate());
        orderNew.setCustomerByCustomerId(customer);
        orderNew.setOrderStatusByStatus(orderStatus);

        ordersRepository.save(orderNew);

        return new ResponseEntity<>(orderNew, HttpStatus.CREATED);
    }
}