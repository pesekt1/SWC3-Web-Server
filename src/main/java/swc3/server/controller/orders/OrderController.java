package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.models.Order;
import swc3.server.models.OrderItem;
import swc3.server.models.OrderItemNote;
import swc3.server.repository.OrderItemNoteRepository;
import swc3.server.repository.OrderItemRepository;
import swc3.server.repository.OrderRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderRepository ordersRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemNoteRepository orderItemNoteRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.addAll(ordersRepository.findAll());

        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order _order = ordersRepository.save(order);

        Collection<OrderItem> orderItems = _order.getOrderItemsByOrderId();
        for (OrderItem orderItem:orderItems) {
            orderItem.setOrderId(_order.getOrderId());
            Collection<OrderItemNote> orderItemNotes = orderItem.getOrderItemNotes();

            OrderItem _orderItem = orderItemRepository.save(orderItem);

            for (OrderItemNote orderItemNote:orderItemNotes) {
                orderItemNote.setOrderItems(_orderItem);
                orderItemNoteRepository.save(orderItemNote);
            }
        }

        return new ResponseEntity<>(_order, HttpStatus.CREATED);
    }
}