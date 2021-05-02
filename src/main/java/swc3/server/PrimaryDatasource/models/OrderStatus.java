package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatus that = (OrderStatus) o;

        if (orderStatusId != that.orderStatusId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) orderStatusId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
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
