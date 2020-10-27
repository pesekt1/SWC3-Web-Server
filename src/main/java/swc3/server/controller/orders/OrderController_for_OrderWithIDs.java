package swc3.server.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.models.models2.OrderItem2;
import swc3.server.models.models2.OrderItemNote2;
import swc3.server.models.models2.OrderWithIDs;
import swc3.server.repository.repository2.OrderItemNoteRepository2;
import swc3.server.repository.repository2.OrderItemRepository2;
import swc3.server.repository.repository2.OrderRepository2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController_for_OrderWithIDs {
    @Autowired
    OrderRepository2 ordersRepository;

    @Autowired
    OrderItemRepository2 orderItemRepository;

    @Autowired
    OrderItemNoteRepository2 orderItemNoteRepository;

    @GetMapping("/ordersWithIDs")
    public ResponseEntity<List<OrderWithIDs>> getAllOrders() {
        List<OrderWithIDs> orders = new ArrayList<>();
        orders.addAll(ordersRepository.findAll());

        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/ordersWithIDs")
    public ResponseEntity<OrderWithIDs> createOrder(@RequestBody OrderWithIDs order) {
        OrderWithIDs _order = ordersRepository.save(order);

        Collection<OrderItem2> orderItems = _order.getOrderItemsByOrderId();
        for (OrderItem2 orderItem:orderItems) {
            orderItem.setOrderId(_order.getOrderId());
            Collection<OrderItemNote2> orderItemNotes = orderItem.getOrderItemNotes();

            OrderItem2 _orderItem = orderItemRepository.save(orderItem);

            for (OrderItemNote2 orderItemNote:orderItemNotes) {
                orderItemNote.setOrderItems(_orderItem);
                orderItemNoteRepository.save(orderItemNote);
            }
        }

        return new ResponseEntity<>(_order, HttpStatus.CREATED);
    }
}