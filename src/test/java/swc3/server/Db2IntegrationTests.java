package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import swc3.server.Db2.models.Tutorial_db2;
import swc3.server.Db2.repo.TutorialRepository_db2;

// integration tests for persistence unit db2,
// transactionManagerDb2 name comes from Db2Config class.
@SpringBootTest
@EnableTransactionManagement
@Transactional("transactionManagerDb2")
class Db2IntegrationTests {

    //repo for tutorial table from db2
    @Autowired
    private TutorialRepository_db2 repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

//    @Test
//    void should_find_no_tutorials_if_repository_is_empty() {
//        Iterable<Tutorial_db2> tutorials = repository.findAll();
//        assertThat(tutorials).isEmpty();
//    }
}
