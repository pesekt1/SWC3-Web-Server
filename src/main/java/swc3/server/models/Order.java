package swc3.server.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "orders")
public class Order {
    private int orderId;
    private Date orderDate;
    private String comments;
    private Date shippedDate;
    private Collection<OrderItem> orderItemsByOrderId;
    private Customer customerByCustomerId;
    private OrderStatus orderStatusByStatus;
    private Shipper shippersByShipperId;

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

    //@JsonManagedReference
    @OneToMany(mappedBy = "ordersByOrderId")
    public Collection<OrderItem> getOrderItemsByOrderId() {
        return orderItemsByOrderId;
    }

    public void setOrderItemsByOrderId(Collection<OrderItem> orderItemsByOrderId) {
        this.orderItemsByOrderId = orderItemsByOrderId;
    }

    //@JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    public Customer getCustomerByCustomerId() {
        return customerByCustomerId;
    }

    public void setCustomerByCustomerId(Customer customersByCustomerId) {
        this.customerByCustomerId = customersByCustomerId;
    }

    //@JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "order_status_id", nullable = false)
    public OrderStatus getOrderStatusByStatus() {
        return orderStatusByStatus;
    }

    public void setOrderStatusByStatus(OrderStatus orderStatusesByStatus) {
        this.orderStatusByStatus = orderStatusesByStatus;
    }

    //@JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "shipper_id", referencedColumnName = "shipper_id")
    public Shipper getShippersByShipperId() {
        return shippersByShipperId;
    }

    public void setShippersByShipperId(Shipper shippersByShipperId) {
        this.shippersByShipperId = shippersByShipperId;
    }
}
