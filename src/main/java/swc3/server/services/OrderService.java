package swc3.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import swc3.server.models.Order;
import swc3.server.models.OrderItem;
import swc3.server.models.OrderItemNote;
import swc3.server.models.ShippedOrderView;
import swc3.server.repository.OrderItemNoteRepository;
import swc3.server.repository.OrderItemRepository;
import swc3.server.repository.OrderRepository;
import swc3.server.repository.ShippedOrderViewRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderService {
    OrderRepository ordersRepository;
    ShippedOrderViewRepository shippedOrderViewRepository;
    OrderItemRepository orderItemRepository;
    OrderItemNoteRepository orderItemNoteRepository;

    public OrderService(OrderRepository ordersRepository, ShippedOrderViewRepository shippedOrderViewRepository, OrderItemRepository orderItemRepository, OrderItemNoteRepository orderItemNoteRepository) {
        this.ordersRepository = ordersRepository;
        this.shippedOrderViewRepository = shippedOrderViewRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemNoteRepository = orderItemNoteRepository;
    }

    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.addAll(ordersRepository.findAll());

        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<List<ShippedOrderView>> getShippedOrders() {
        List<ShippedOrderView> shippedOrders = new ArrayList<>();
        shippedOrders.addAll(shippedOrderViewRepository.findAll());

        if (shippedOrders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(shippedOrders, HttpStatus.OK);
    }

    public ResponseEntity<Order> createOrder(Order order) {
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
