package swc3.server.Datasources.Db3.models;

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
//@Table(name = "tutorials", schema= "dbo", catalog = "swc3_springboot_mssql")
@Table(name = "tutorials", schema = "dbo", catalog = "azureSqlDb")
public class Tutorial_db3 {

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

    public Tutorial_db3(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
