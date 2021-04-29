package swc3.server.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

//lombok project - replace equals, hashCode, setters, getters, noArgsConstructor with these annotations:
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "tutorials", schema = "swc3_springboot")
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Basic@Column(name = "description")
    private String description;

    @Basic@Column(name = "published")
    private Boolean published;

    @Basic@Column(name = "title", nullable = false)
    private String title;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
