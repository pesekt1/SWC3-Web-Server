package swc3.server.PrimaryDatasource.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Table(name = "order_item_notes")
public class OrderItemNote {
    private int noteId;
    private String note;

    private OrderItem orderItems;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "note_id", nullable = false)
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @Basic
    @Column(name = "note", nullable = false, length = 255)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false), @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)})
    public OrderItem getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem orderItems) {
        this.orderItems = orderItems;
    }
}
