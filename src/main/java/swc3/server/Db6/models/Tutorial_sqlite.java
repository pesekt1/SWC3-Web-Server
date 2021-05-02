package swc3.server.Db6.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "tutorials")
public class Tutorial_sqlite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private short id;

    @Basic@Column(name = "description", nullable = false, length = -1)
    private String description;

    @Basic@Column(name = "title", nullable = false, length = -1)
    private String title;

    @Basic@Column(name = "published", nullable = false)
    private short published;

}
