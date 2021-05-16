package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Basic
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Basic
    @Column(name = "comments", nullable = true, length = 2000)
    private String comments;

    @Basic
    @Column(name = "shipped_date", nullable = true)
    private Date shippedDate;

    @OneToMany(mappedBy = "ordersByOrderId")
    private Collection<OrderItem> orderItemsByOrderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customerByCustomerId;

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "order_status_id", nullable = false)
    private OrderStatus orderStatusByStatus;

    @ManyToOne
    @JoinColumn(name = "shipper_id", referencedColumnName = "shipper_id")
    private Shipper shippersByShipperId;

}
