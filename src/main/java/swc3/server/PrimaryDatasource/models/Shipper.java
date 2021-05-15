package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode
@Entity
@Table(name = "shippers")
public class Shipper {
    private short shipperId;
    private String name;
    private Collection<Order> ordersByShipperId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "shipper_id", nullable = false)
    public short getShipperId() {
        return shipperId;
    }

    public void setShipperId(short shipperId) {
        this.shipperId = shipperId;
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
    @OneToMany(mappedBy = "shippersByShipperId")
    public Collection<Order> getOrdersByShipperId() {
        return ordersByShipperId;
    }

    public void setOrdersByShipperId(Collection<Order> ordersByShipperId) {
        this.ordersByShipperId = ordersByShipperId;
    }
}
