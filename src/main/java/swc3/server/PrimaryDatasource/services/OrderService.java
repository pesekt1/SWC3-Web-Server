package swc3.server.PrimaryDatasource.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.*;
import swc3.server.PrimaryDatasource.pojo.OrderPojo;
import swc3.server.PrimaryDatasource.repository.OrderItemNoteRepository;
import swc3.server.PrimaryDatasource.repository.OrderItemRepository;
import swc3.server.PrimaryDatasource.repository.OrderRepository;
import swc3.server.PrimaryDatasource.repository.ShippedOrderViewRepository;
import swc3.server.PrimaryDatasource.services.invoice.InvoiceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderService {

    private static final byte NEW_ORDER_STATUS = (byte)4;
    private static final int INVOICE_DUE_PERIOD = 30;

    OrderRepository ordersRepository;
    ShippedOrderViewRepository shippedOrderViewRepository;
    OrderItemRepository orderItemRepository;
    OrderItemNoteRepository orderItemNoteRepository;
    InvoiceService invoiceService;

    @Autowired
    public OrderService(
            OrderRepository ordersRepository,
            ShippedOrderViewRepository shippedOrderViewRepository,
            OrderItemRepository orderItemRepository,
            OrderItemNoteRepository orderItemNoteRepository,
            @Qualifier("invoiceServiceImpl") InvoiceService invoiceService
    ) {
            this.ordersRepository = ordersRepository;
            this.shippedOrderViewRepository = shippedOrderViewRepository;
            this.orderItemRepository = orderItemRepository;
            this.orderItemNoteRepository = orderItemNoteRepository;
            this.invoiceService = invoiceService;
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

    public ResponseEntity<Order> createOrder(OrderPojo order) {
        long invoicePriceSum = 0;
        //BigDecimal orderItemPriceSum = BigDecimal.ZERO;
        Order newOrder = new Order();
        newOrder.setComments(order.getComments());
        newOrder.setCustomerId(order.getCustomerId());
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setStatus(NEW_ORDER_STATUS);
        newOrder.setOrderItems(order.getOrderItems());

        Order savedOrder = ordersRepository.save(newOrder);

        Collection<OrderItem> orderItems = savedOrder.getOrderItems();
        for (OrderItem orderItem:orderItems) {

            invoicePriceSum += orderItem.getUnitPrice() * orderItem.getQuantity();

            orderItem.setOrderId(savedOrder.getOrderId());
            Collection<OrderItemNote> orderItemNotes = orderItem.getOrderItemNotes();

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            for (OrderItemNote orderItemNote:orderItemNotes) {
                orderItemNote.setOrderId(savedOrderItem.getOrderId());
                orderItemNote.setProductId(savedOrderItem.getProductId());
                orderItemNoteRepository.save(orderItemNote);
            }
        }

        var invoice = new Invoice();
        invoice.setNumber(ObjectId.get().toString());
        invoice.setInvoiceTotal(invoicePriceSum);
        invoice.setPaymentTotal(0);
        invoice.setInvoiceDate(newOrder.getOrderDate());
        invoice.setDueDate(newOrder.getOrderDate().plusDays(INVOICE_DUE_PERIOD));
        invoice.setOrderId(newOrder.getOrderId());

        invoiceService.create(invoice);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
