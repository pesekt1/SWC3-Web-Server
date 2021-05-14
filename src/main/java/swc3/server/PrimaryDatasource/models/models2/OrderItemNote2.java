package swc3.server.PrimaryDatasource.models.models2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "order_item_notes", schema = "swc3_springboot")
public class OrderItemNote2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "note_id", nullable = false)
    private int noteId;

    @Basic
    @Column(name = "note", nullable = false, length = 255)
    private String note;

    @JsonBackReference
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false), @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)})
    private OrderItem2 orderItems;
}
