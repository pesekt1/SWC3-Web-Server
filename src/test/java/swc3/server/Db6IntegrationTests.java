package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.Datasources.Db6.models.TutorialSqlite;
import swc3.server.Datasources.Db6.repo.TutorialRepositorySqlite;

import static org.assertj.core.api.Assertions.assertThat;

// integration tests for persistence unit db6

@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {TutorialRepositorySqlite.class})
@ComponentScan(resourcePattern = "**/Db6Config.class")
@Transactional(transactionManager = "transactionManagerDb6")
class Db6IntegrationTests {

    //repo for tutorial table from db6
    @Autowired
    private TutorialRepositorySqlite repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<TutorialSqlite> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
}
