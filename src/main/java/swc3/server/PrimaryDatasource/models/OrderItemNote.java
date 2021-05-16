package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "order_item_notes")
public class OrderItemNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "note_id", nullable = false)
    private int noteId;

    @Basic
    @Column(name = "note", nullable = false, length = 255)
    private String note;

    @Basic
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Basic
    @Column(name = "product_id", nullable = false)
    private int productId;

    @JsonBackReference
    @ManyToOne
    @JoinColumns(
            {
                    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable = false, updatable = false),
                    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
            })
    private OrderItem orderItems;

}
