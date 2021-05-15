package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.Datasources.Db2.models.Tutorial_db2;
import swc3.server.Datasources.Db2.repo.TutorialRepository_db2;
import swc3.server.config.Db2Config;

import static org.assertj.core.api.Assertions.assertThat;

// integration tests for persistence unit db2,

@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {Db2Config.class, TutorialRepository_db2.class})
@ComponentScan(resourcePattern = "**/Db2Config.class")
@Transactional(transactionManager = "transactionManagerDb2")
class Db2IntegrationTests {

    //repo for tutorial table from db2
    @Autowired
    private TutorialRepository_db2 repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial_db2> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
}
