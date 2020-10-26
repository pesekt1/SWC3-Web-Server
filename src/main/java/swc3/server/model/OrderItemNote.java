package swc3.server.model;

import javax.persistence.*;

@Entity
@Table(name = "order_item_notes", schema = "swc3_springboot")
public class OrderItemNote {
    private int noteId;
    private String note;

    @Id
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

        OrderItemNote that = (OrderItemNote) o;

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
}
