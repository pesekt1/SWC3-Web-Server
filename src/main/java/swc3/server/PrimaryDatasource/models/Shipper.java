package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "shippers", schema = "swc3_springboot")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id", nullable = false)
    private short shipperId;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
