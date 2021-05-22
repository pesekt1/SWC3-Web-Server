package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "order_items", schema = "swc3_springboot")
@IdClass(OrderItemPK.class)
public class OrderItem {
    @Id
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 2)
    private double unitPrice;

    @OneToMany(mappedBy = "orderItems")
    private Collection<OrderItemNote> orderItemNotes;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable=false, updatable=false)
    private Order ordersByOrderId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    private Product product;

    @Transient
    public double getTotalPrice() {
        return getUnitPrice() * getQuantity();
    }
}
