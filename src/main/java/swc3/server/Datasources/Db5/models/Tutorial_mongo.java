package swc3.server.Datasources.Db5.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "tutorials")
public class Tutorial_mongo {

    @Id
    private String id;
    private String title;
    private String description;
    private boolean published;

    public Tutorial_mongo(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
