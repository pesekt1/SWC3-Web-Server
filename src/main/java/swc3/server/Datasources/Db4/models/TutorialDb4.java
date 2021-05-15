package swc3.server.Datasources.Db4.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//lombok project - replace equals, hashCode, setters, getters, noArgsConstructor with these annotations:
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "tutorials", schema = "public", catalog = "swc3Postgres")
public class TutorialDb4 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "published")
    private Boolean published;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    public TutorialDb4(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
