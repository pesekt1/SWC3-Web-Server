package swc3.server.PrimaryDatasource.models.models2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "order_items", schema = "swc3_springboot")
@IdClass(OrderItem2PK.class)
public class OrderItem2 {
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
    private BigDecimal unitPrice;

    @JsonBackReference
    @ManyToOne@JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable=false, updatable=false)
    private OrderWithIDs ordersByOrderId;

    @OneToMany(mappedBy = "orderItems")
    private Collection<OrderItemNote2> orderItemNotes;

}


