package swc3.server.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "tutorials")
public class Tutorial {
    @Id@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")@GenericGenerator(name = "native", strategy = "native")@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic@Column(name = "published", nullable = true)
    private Boolean published;
    @Basic@Column(name = "title", nullable = false, length = 255)
    private String title;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

//    public Tutorial() {
//    }

}
