package swc3.server.Db6.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
public class Tutorial_sqlite {
    private short id;
    private String description;
    private String title;
    private short published;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "title", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "published", nullable = false)
    public short getPublished() {
        return published;
    }

    public void setPublished(short published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutorial_sqlite tutorials = (Tutorial_sqlite) o;

        if (id != tutorials.id) return false;
        if (published != tutorials.published) return false;
        if (description != null ? !description.equals(tutorials.description) : tutorials.description != null)
            return false;
        if (title != null ? !title.equals(tutorials.title) : tutorials.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (int) published;
        return result;
    }
}
