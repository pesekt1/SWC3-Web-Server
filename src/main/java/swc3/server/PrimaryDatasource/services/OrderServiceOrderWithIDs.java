package swc3.server.PrimaryDatasource.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.models2.OrderItem2;
import swc3.server.PrimaryDatasource.models.models2.OrderItemNote2;
import swc3.server.PrimaryDatasource.models.models2.OrderWithIDs;
import swc3.server.PrimaryDatasource.repository.repository2.OrderItemNoteRepository2;
import swc3.server.PrimaryDatasource.repository.repository2.OrderItemRepository2;
import swc3.server.PrimaryDatasource.repository.repository2.OrderRepository2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderServiceOrderWithIDs {
    OrderRepository2 ordersRepository;
    OrderItemRepository2 orderItemRepository;
    OrderItemNoteRepository2 orderItemNoteRepository;

    public OrderServiceOrderWithIDs(OrderRepository2 ordersRepository, OrderItemRepository2 orderItemRepository, OrderItemNoteRepository2 orderItemNoteRepository) {
        this.ordersRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemNoteRepository = orderItemNoteRepository;
    }

    public ResponseEntity<List<OrderWithIDs>> getAllOrders() {
        List<OrderWithIDs> orders = new ArrayList<>();
        orders.addAll(ordersRepository.findAll());

        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<OrderWithIDs> createOrder(OrderWithIDs order) {
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
