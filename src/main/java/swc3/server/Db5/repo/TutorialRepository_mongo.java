package swc3.server.Db5.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import swc3.server.Db5.models.Tutorial_mongo;

import java.util.List;

public interface TutorialRepository_mongo extends MongoRepository<Tutorial_mongo, String> {
    List<Tutorial_mongo> findByPublished(boolean published);
    List<Tutorial_mongo> findByTitleContaining(String title);
}