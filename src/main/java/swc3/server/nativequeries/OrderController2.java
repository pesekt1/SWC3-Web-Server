package swc3.server.nativequeries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.models.Order;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/extra")
public class OrderController2 {

    @Autowired
    OrdersRepository2 ordersRepository2;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = ordersRepository2.findAll(Sort.by(Sort.Direction.DESC, "orderId"));
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders-shipped")
    public ResponseEntity<Collection<Order>> getAllShippedOrders() {
        Collection<Order> orders = ordersRepository2.findAllShippedOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders-pagination")
    public ResponseEntity<Collection<Order>> getAllOrdersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Order> ordersPage = ordersRepository2.findAllOrdersWithPagination(paging);
        Collection<Order> orders = ordersPage.getContent();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders-cust-ship")
    public ResponseEntity<Collection<Order>> getOrderByCustomerAndShipper(
            @RequestParam(defaultValue = "0") int customerId,
            @RequestParam(defaultValue = "0") short shipperId) {
        Collection<Order> orders = ordersRepository2.findOrderByCustomerAndShipper(customerId,shipperId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders-cust-ship-named-params")
    public ResponseEntity<Collection<Order>> getOrderByCustomerAndShipperNamedParams(
            @RequestParam(defaultValue = "0") int customerId,
            @RequestParam(defaultValue = "0") short shipperId) {
        Collection<Order> orders = ordersRepository2.findOrderByCustomerAndShipperNamedParams(customerId, shipperId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders-by-customer-names")
    public ResponseEntity<Collection<Order>> getOrdersByCustomerNameList(
            @RequestParam(defaultValue = "") Collection<String> names) {
        Collection<Order> orders = ordersRepository2.findOrdersByCustomerNameList(names);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



}
