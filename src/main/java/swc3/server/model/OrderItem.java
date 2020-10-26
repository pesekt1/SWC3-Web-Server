package swc3.server.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items", schema = "swc3_springboot")
@IdClass(OrderItemPK.class)
public class OrderItem {
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal unitPrice;

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

        OrderItem orderItem = (OrderItem) o;

        if (orderId != orderItem.orderId) return false;
        if (productId != orderItem.productId) return false;
        if (quantity != orderItem.quantity) return false;
        if (unitPrice != null ? !unitPrice.equals(orderItem.unitPrice) : orderItem.unitPrice != null) return false;

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
}
