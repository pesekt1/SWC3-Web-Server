package swc3.server.models.models2;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "order_items", schema = "swc3_springboot")
@IdClass(OrderItem2PK.class)
public class OrderItem2 {
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal unitPrice;
    private OrderWithIDs ordersByOrderId;
    private Collection<OrderItemNote2> orderItemNotes;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem2 that = (OrderItem2) o;

        if (orderId != that.orderId) return false;
        if (productId != that.productId) return false;
        if (quantity != that.quantity) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + productId;
        result = 31 * result + quantity;
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "orderItems")
    public Collection<OrderItemNote2> getOrderItemNotes() {
        return orderItemNotes;
    }

    public void setOrderItemNotes(Collection<OrderItemNote2> orderItemNotes) {
        this.orderItemNotes = orderItemNotes;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable=false, updatable=false)
    public OrderWithIDs getOrdersByOrderId() {
        return ordersByOrderId;
    }

    public void setOrdersByOrderId(OrderWithIDs ordersByOrderId) {
        this.ordersByOrderId = ordersByOrderId;
    }
}
