package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@EqualsAndHashCode
@Entity
@Table(name = "order_items")
@IdClass(OrderItemPK.class)
public class OrderItem {
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal unitPrice;
    private Collection<OrderItemNote> orderItemNotes;
    private Order ordersByOrderId;
    private Product productsByProductId;

    @Id
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @OneToMany(mappedBy = "orderItems")
    public Collection<OrderItemNote> getOrderItemNotes() {
        return orderItemNotes;
    }

    public void setOrderItemNotes(Collection<OrderItemNote> orderItemNotes) {
        this.orderItemNotes = orderItemNotes;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable=false, updatable=false)
    public Order getOrdersByOrderId() {
        return ordersByOrderId;
    }

    public void setOrdersByOrderId(Order ordersByOrderId) {
        this.ordersByOrderId = ordersByOrderId;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    public Product getProductsByProductId() {
        return productsByProductId;
    }

    public void setProductsByProductId(Product productsByProductId) {
        this.productsByProductId = productsByProductId;
    }
}
