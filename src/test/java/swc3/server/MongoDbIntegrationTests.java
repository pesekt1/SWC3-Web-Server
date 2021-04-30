package swc3.server;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import swc3.server.Db5.models.Tutorial_mongo;
import swc3.server.Db5.repo.TutorialRepository_mongo;

@RunWith(SpringRunner.class)
@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MongoDbIntegrationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TutorialRepository_mongo repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial_mongo> tutorials = repository.findAll();
        Assertions.assertThat(tutorials).isEmpty();
    }

    @Test
    void collection_exists_after_creating_document() {
        Tutorial_mongo tutorial = new Tutorial_mongo("t1","d1",false);
        this.repository.save(tutorial);
        Assertions.assertThat(this.mongoTemplate.collectionExists("tutorials")).isTrue();
    }


}