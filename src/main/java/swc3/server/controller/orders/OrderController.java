package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import swc3.server.models.Order;
import swc3.server.models.ShippedOrderView;
import swc3.server.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/shipped-orders")
    public ResponseEntity<List<ShippedOrderView>> getShippedOrders() {
        return orderService.getShippedOrders();
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
}