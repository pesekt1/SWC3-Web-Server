package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;
import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@Transactional("transactionManager")
public class JpaMultipleDBIntegrationTest {

//    @Autowired
//    private TutorialRepository repository;
//
//    @BeforeEach
//    public void init(){
//    repository.deleteAll();
//    }
//
//    @Test
//    //@Transactional("transactionManager")
//    public void should_find_no_tutorials_if_repository_is_empty() {
//        Iterable<Tutorial> tutorials = repository.findAll();
//        assertThat(tutorials).isEmpty();
//    }
}