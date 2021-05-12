package swc3.server.Datasources.Db5.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db5.models.TutorialMongo;

import java.util.List;

@RepositoryRestResource(path = "tutorialsMongoDB") //overriding the auto-generated api path
public interface TutorialMongoRepository extends MongoRepository<TutorialMongo, ObjectId> {
    List<TutorialMongo> findByPublished(boolean published);
    List<TutorialMongo> findByTitleContaining(String title);
}