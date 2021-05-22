package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "order_statuses", schema = "swc3_springboot")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id", nullable = false)
    private byte orderStatusId;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
