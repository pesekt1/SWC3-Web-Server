package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableTransactionManagement
@Transactional("transactionManager")
public class Db1IntegrationTests {

    @Autowired
    private TutorialRepository repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    @Transactional("transactionManager")
    public void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

}
