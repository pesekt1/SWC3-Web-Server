package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")@GenericGenerator(name = "native", strategy = "native")@Column(name = "customer_id", nullable = false)
    private int customerId;

    @Basic@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic@Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Basic@Column(name = "birth_date", nullable = true)
    private Date birthDate;

    @Basic@Column(name = "phone", nullable = true, length = 50)
    private String phone;

    @Basic@Column(name = "address", nullable = false, length = 50)
    private String address;

    @Basic@Column(name = "city", nullable = false, length = 50)
    private String city;

    @Basic@Column(name = "state", nullable = false, length = 2)
    private String state;

    @Basic@Column(name = "points", nullable = false)
    private int points;

    @JsonBackReference@OneToMany(mappedBy = "customerByCustomerId")
    private Collection<Order> ordersByCustomerId;

}
