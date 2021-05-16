package swc3.server.PrimaryDatasource.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.*;
import swc3.server.PrimaryDatasource.pojo.OrderPojo;
import swc3.server.PrimaryDatasource.repository.OrderItemNoteRepository;
import swc3.server.PrimaryDatasource.repository.OrderItemRepository;
import swc3.server.PrimaryDatasource.repository.OrderRepository;
import swc3.server.PrimaryDatasource.repository.ShippedOrderViewRepository;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderService {

    //OrderStatus newOrderStatus;

    OrderRepository ordersRepository;
    ShippedOrderViewRepository shippedOrderViewRepository;
    OrderItemRepository orderItemRepository;
    OrderItemNoteRepository orderItemNoteRepository;

    public OrderService(OrderRepository ordersRepository, ShippedOrderViewRepository shippedOrderViewRepository, OrderItemRepository orderItemRepository, OrderItemNoteRepository orderItemNoteRepository) {
        this.ordersRepository = ordersRepository;
        this.shippedOrderViewRepository = shippedOrderViewRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemNoteRepository = orderItemNoteRepository;

//        this.newOrderStatus = new OrderStatus();
//        this.newOrderStatus.setOrderStatusId((byte)4); //new order
    }

    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = new ArrayList<>(ordersRepository.findAll());

        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<List<ShippedOrderView>> getShippedOrders() {
        List<ShippedOrderView> shippedOrders = new ArrayList<>(shippedOrderViewRepository.findAll());

        if (shippedOrders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(shippedOrders, HttpStatus.OK);
    }

    public ResponseEntity<Order> createOrder(Order order) {
//        Order newOrder = new Order();
//        newOrder.setComments(order.getComments());
//        newOrder.setCustomerByCustomerId(order.getCustomerByCustomerId());
//        newOrder.setOrderDate(new Date(new java.util.Date().getTime()));
//        newOrder.setOrderStatusByStatus(newOrderStatus);
//        newOrder.setOrderItemsByOrderId(order.getOrderItemsByOrderId());

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
