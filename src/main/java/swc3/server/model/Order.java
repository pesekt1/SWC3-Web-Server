package swc3.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders", schema = "swc3_springboot")
public class Order {
    private int orderId;
    private Date orderDate;
    private String comments;
    private Date shippedDate;
    private Customer customersByCustomerId;
    private OrderStatus orderStatusesByStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_date", nullable = false)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Basic
    @Column(name = "comments", nullable = true, length = 2000)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Basic
    @Column(name = "shipped_date", nullable = true)
    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (comments != null ? !comments.equals(order.comments) : order.comments != null) return false;
        if (shippedDate != null ? !shippedDate.equals(order.shippedDate) : order.shippedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (shippedDate != null ? shippedDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    public Customer getCustomerByCustomerId() {
        return customersByCustomerId;
    }

    public void setCustomerByCustomerId(Customer customersByCustomerId) {
        this.customersByCustomerId = customersByCustomerId;
    }

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "order_status_id", nullable = false)
    public OrderStatus getOrderStatusByStatus() {
        return orderStatusesByStatus;
    }

    public void setOrderStatusByStatus(OrderStatus orderStatusesByStatus) {
        this.orderStatusesByStatus = orderStatusesByStatus;
    }
}
