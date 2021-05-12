package swc3.server.PrimaryDatasource.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Order;
import swc3.server.PrimaryDatasource.models.OrderItem;
import swc3.server.PrimaryDatasource.models.OrderItemNote;
import swc3.server.PrimaryDatasource.models.ShippedOrderView;
import swc3.server.PrimaryDatasource.repository.OrderItemNoteRepository;
import swc3.server.PrimaryDatasource.repository.OrderItemRepository;
import swc3.server.PrimaryDatasource.repository.OrderRepository;
import swc3.server.PrimaryDatasource.repository.ShippedOrderViewRepository;

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
        Order savedOrder = ordersRepository.save(order);

        Collection<OrderItem> orderItems = savedOrder.getOrderItemsByOrderId();
        for (OrderItem orderItem:orderItems) {
            orderItem.setOrderId(savedOrder.getOrderId());
            Collection<OrderItemNote> orderItemNotes = orderItem.getOrderItemNotes();

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            for (OrderItemNote orderItemNote:orderItemNotes) {
                orderItemNote.setOrderItems(savedOrderItem);
                orderItemNoteRepository.save(orderItemNote);
            }
        }
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
