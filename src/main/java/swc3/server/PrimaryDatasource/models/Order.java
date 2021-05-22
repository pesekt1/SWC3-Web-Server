package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "orders", schema = "swc3_springboot")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Basic
    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Basic
    //@JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Basic
    @Column(name = "status", nullable = false)
    private byte status;

    @Basic
    @Column(name = "comments", nullable = true, length = 2000)
    private String comments;

    @Basic
    @Column(name = "shipped_date", nullable = true)
    private LocalDate shippedDate;

    @Basic
    @Column(name = "shipper_id", nullable = true)
    private Short shipperId;

    @OneToMany(mappedBy = "ordersByOrderId")
    private Collection<OrderItem> orderItems;

    //Hibernate will ignore this field
    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0;
        for (OrderItem oi : getOrderItems()) {
            sum += oi.getTotalPrice();
        }
        return sum;
    }

    @Transient
    public int getProductsNumber(){
        int sum = 0;
        for (OrderItem oi : getOrderItems()){
            sum += oi.getQuantity();
        }
        return sum;
    }
}
