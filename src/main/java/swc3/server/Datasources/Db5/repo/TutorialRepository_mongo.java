package swc3.server.Datasources.Db5.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db5.models.Tutorial_mongo;

import java.util.List;

@RepositoryRestResource(path = "tutorialsMongoDB") //overriding the auto-generated api path
public interface TutorialRepository_mongo extends MongoRepository<Tutorial_mongo, String> {
    List<Tutorial_mongo> findByPublished(boolean published);
    List<Tutorial_mongo> findByTitleContaining(String title);
}