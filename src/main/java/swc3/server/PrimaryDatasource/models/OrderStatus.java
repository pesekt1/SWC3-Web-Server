package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode
@Entity
@Table(name = "order_statuses")
public class OrderStatus {
    private byte orderStatusId;
    private String name;
    private Collection<Order> ordersByOrderStatusId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "order_status_id", nullable = false)
    public byte getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(byte orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "orderStatusByStatus")
    public Collection<Order> getOrdersByOrderStatusId() {
        return ordersByOrderStatusId;
    }

    public void setOrdersByOrderStatusId(Collection<Order> ordersByOrderStatusId) {
        this.ordersByOrderStatusId = ordersByOrderStatusId;
    }
}
