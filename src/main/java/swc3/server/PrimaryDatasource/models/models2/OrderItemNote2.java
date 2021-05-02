package swc3.server.PrimaryDatasource.models.models2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "order_item_notes", schema = "swc3_springboot")
public class OrderItemNote2 {
    private int noteId;
    private String note;
    private OrderItem2 orderItems;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemNote2 that = (OrderItemNote2) o;

        if (noteId != that.noteId) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = noteId;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false), @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)})
    public OrderItem2 getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem2 orderItems) {
        this.orderItems = orderItems;
    }
}
