package swc3.server;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import swc3.server.Datasources.Db5.models.TutorialMongo;
import swc3.server.Datasources.Db5.repo.TutorialMongoRepository;

//@RunWith(SpringRunner.class)
@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MongoDbIntegrationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TutorialMongoRepository repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<TutorialMongo> tutorials = repository.findAll();
        Assertions.assertThat(tutorials).isEmpty();
    }

    @Test
    void collection_exists_after_creating_document() {
        TutorialMongo tutorial = new TutorialMongo("t1","d1",false);
        this.repository.save(tutorial);
        Assertions.assertThat(this.mongoTemplate.collectionExists("tutorials")).isTrue();
    }


}