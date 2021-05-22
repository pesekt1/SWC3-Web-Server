package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "payments", schema = "swc3_springboot")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private int paymentId;

    @Basic
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    private long amount;

    @Basic
    @Column(name = "payment_method", nullable = false)
    private byte paymentMethod;

    @Basic
    @Column(name = "customer_id", nullable = false)
    private int customerId;

}
