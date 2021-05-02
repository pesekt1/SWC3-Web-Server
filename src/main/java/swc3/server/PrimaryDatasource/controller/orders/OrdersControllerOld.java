package swc3.server.PrimaryDatasource.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.PrimaryDatasource.models.Order;
import swc3.server.PrimaryDatasource.repository.OrderRepository;

//saves given order to the database
//but not saving orderItems or OrderItemNotes

@RestController
@RequestMapping("/api")
public class OrdersControllerOld {
    @Autowired
    OrderRepository ordersRepository;

    //not doing integrity checks....
    //if we give non existing customer id or status id it will throw an exception
    //... we will get internal server error
    @PostMapping("/ordersOld")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order _order = ordersRepository.save(order);
        return new ResponseEntity<>(_order, HttpStatus.CREATED);
    }
}