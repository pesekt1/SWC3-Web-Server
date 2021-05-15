package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.Datasources.Db3.models.Tutorial_db3;
import swc3.server.Datasources.Db3.repo.TutorialRepository_db3;
import swc3.server.Datasources.Db6.models.Tutorial_sqlite;
import swc3.server.Datasources.Db6.repo.TutorialRepository_sqlite;

import static org.assertj.core.api.Assertions.assertThat;

// integration tests for persistence unit db6

@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {TutorialRepository_sqlite.class})
@ComponentScan(resourcePattern = "**/Db6Config.class")
@Transactional(transactionManager = "transactionManagerDb6")
class Db6IntegrationTests {

    //repo for tutorial table from db6
    @Autowired
    private TutorialRepository_sqlite repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial_sqlite> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
}
