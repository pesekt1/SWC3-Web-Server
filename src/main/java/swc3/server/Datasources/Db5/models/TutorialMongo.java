package swc3.server.Datasources.Db5.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "tutorials")
public class TutorialMongo {

    @Id
    private ObjectId id;
    private String title;
    private String description;
    private boolean published;

    public TutorialMongo(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
