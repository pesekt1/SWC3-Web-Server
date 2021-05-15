package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.Datasources.Db4.models.TutorialDb4;
import swc3.server.Datasources.Db4.repo.TutorialRepositoryDb4;

import static org.assertj.core.api.Assertions.assertThat;

// integration tests for persistence unit db3

@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {TutorialRepositoryDb4.class})
@ComponentScan(resourcePattern = "**/Db4Config.class")
@Transactional(transactionManager = "transactionManagerDb4")
class Db4IntegrationTests {

    //repo for tutorial table from db4
    @Autowired
    private TutorialRepositoryDb4 repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<TutorialDb4> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
}
