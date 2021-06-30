package swc3.server.PrimaryDatasource.models;

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

//if we want batch inserts and updates we need to use SEQUENCE which is not implemented in MySQL - we need an extra table for that.
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
//    @SequenceGenerator(name = "seqGen", sequenceName = "hibernate_sequence", initialValue = 1)
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    //@GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) //refers to the table column
    private long id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "published")
    private Boolean published;

    //@Basic(optional = false, fetch = FetchType.LAZY) - refers to the JPA entity
    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
