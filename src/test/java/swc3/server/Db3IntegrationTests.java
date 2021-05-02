package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import swc3.server.Datasources.Db3.repo.TutorialRepository_db3;
import swc3.server.config.Db1Config;
import swc3.server.config.Db3Config;

// This does not work correctly. I dont know how to avoid @AutoConfigureDataJpa and how to apply the right datasource

// integration tests for persistence unit db3,
// transactionManagerDb3 name comes from Db3Config class.
@SpringBootTest
@AutoConfigureDataJpa //works with the primary datasource - I dont know how to use the Db3 datasource...
//i need Db1Config.class because of the transactionManager, i cannot figure out how to use transactionManagerDb2
@ContextConfiguration(classes = {Db1Config.class, Db3Config.class, TutorialRepository_db3.class})
@Transactional("transactionManagerDb3") //does no work - it uses transactionManager - from the primary source...
class Db3IntegrationTests {

    //repo for tutorial table from db3
    @Autowired
    private TutorialRepository_db3 repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

//    @Test
//    void should_find_no_tutorials_if_repository_is_empty() {
//        Iterable<Tutorial_db3> tutorials = repository.findAll();
//        assertThat(tutorials).isEmpty();
//    }
}
