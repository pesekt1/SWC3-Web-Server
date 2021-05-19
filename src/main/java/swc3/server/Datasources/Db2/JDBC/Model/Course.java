package swc3.server.Datasources.Db2.JDBC.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class Course {

    private int courseId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    @URL
    private String link;

    public Course(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }
}