package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "invoices", schema = "swc3_springboot")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

    @Basic
    @Column(name = "number", nullable = false, length = 50)
    private String number;

    @Basic
    @Column(name = "invoice_total", nullable = false, precision = 2)
    private long invoiceTotal;

    @Basic
    @Column(name = "payment_total", nullable = false, precision = 2)
    private long paymentTotal;

    @Basic
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Basic
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Basic
    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;

    @Basic
    @Column(name = "order_id", nullable = false)
    private int orderId;
}
