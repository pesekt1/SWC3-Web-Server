package swc3.server.controller.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import swc3.server.models.models2.OrderWithIDs;
import swc3.server.services.OrderServiceOrderWithIDs;

@RestController
@RequestMapping("/api")
public class OrderWithIDsController {
    OrderServiceOrderWithIDs orderServiceOrderWithIDs;

    public OrderWithIDsController(OrderServiceOrderWithIDs orderServiceOrderWithIDs) {
        this.orderServiceOrderWithIDs = orderServiceOrderWithIDs;
    }

    @GetMapping("/ordersWithIDs")
    public ResponseEntity<List<OrderWithIDs>> getAllOrders() {
        return orderServiceOrderWithIDs.getAllOrders();
    }

    @PostMapping("/ordersWithIDs")
    public ResponseEntity<OrderWithIDs> createOrder(@RequestBody OrderWithIDs order) {
        return orderServiceOrderWithIDs.createOrder(order);
    }
}