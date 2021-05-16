package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Date;

//mapping a view

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Immutable //so that we cannot change data through this class
@Table(name = "shipped_orders", schema = "swc3_springboot") //@Table but it is a view
public class ShippedOrderView {

    @Basic
    @Column(name = "order_id", nullable = false)
    @Id
    private int orderId;

    @Basic
    @Column(name = "shipped_date")
    private Date shippedDate;

    @Basic
    @Column(name = "customer", length = 101)
    private String customer;

    @Basic
    @Column(name = "shipper", nullable = false, length = 50)
    private String shipper;
}
