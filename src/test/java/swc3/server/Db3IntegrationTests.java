package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import swc3.server.Db3.models.Tutorial_db3;
import swc3.server.Db3.repo.TutorialRepository_db3;

import javax.persistence.PersistenceContext;


// integration tests for persistence unit db3,
// transactionManagerDb3 name comes from Db3Config class.
@SpringBootTest
@EnableTransactionManagement
@Transactional("transactionManagerDb3")
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
