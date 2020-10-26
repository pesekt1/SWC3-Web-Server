package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.model.Order;
import swc3.server.repository.OrderRepository;


//saves given order to the database

@RestController
@RequestMapping("/api")
public class OrdersController {
    @Autowired
    OrderRepository ordersRepository;

    //not doing integrity checks....
    //if we give non existing customer id or status id it will throw an exception
    //... we will get internal server error
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        ordersRepository.save(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}