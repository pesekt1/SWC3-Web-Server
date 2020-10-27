package swc3.server.models;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
public class Tutorial {
    private long id;
    private String description;
    private Boolean published;
    private String title;

    public Tutorial(String description,String title, Boolean published) {
        this.description = description;
        this.published = published;
        this.title = title;
    }

    public Tutorial() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "published", nullable = true)
    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutorial tutorial = (Tutorial) o;

        if (id != tutorial.id) return false;
        if (description != null ? !description.equals(tutorial.description) : tutorial.description != null)
            return false;
        if (published != null ? !published.equals(tutorial.published) : tutorial.published != null) return false;
        if (title != null ? !title.equals(tutorial.title) : tutorial.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (published != null ? published.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
