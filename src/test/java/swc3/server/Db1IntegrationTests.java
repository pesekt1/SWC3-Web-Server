package swc3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@SpringBootTest
@EnableTransactionManagement
@Transactional("transactionManager")
public class Db1IntegrationTests {

    @Autowired
    private TutorialRepository repository;

    //@Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    public void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    void should_store_a_tutorial() {
        Tutorial tutorial = repository.save(new Tutorial("Tut title", "Tut desc", true));
        assertThat(tutorial)
            .hasFieldOrPropertyWithValue("title", "Tut title")
            .hasFieldOrPropertyWithValue("description", "Tut desc")
            .hasFieldOrPropertyWithValue("published", true);
    }

    @Test
    void should_find_all_tutorials() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);

        repository.saveAll(Arrays.asList(tut1, tut2, tut3));
        assertThat(repository.findAll()).hasSize(3).contains(tut1, tut2, tut3);
    }

    @Test
    void should_find_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        repository.saveAll(Arrays.asList(tut1, tut2));

        Tutorial foundTutorial = repository.findById(tut2.getId()).get();
        assertThat(foundTutorial).isEqualTo(tut2);
    }

    @Test
    void should_find_published_tutorials() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        repository.saveAll(Arrays.asList(tut1, tut2, tut3));

        Iterable<Tutorial> tutorials = repository.findByPublished(true);
        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    void should_find_tutorials_by_title_containing_string() {
        Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
        Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
        Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
        repository.saveAll(Arrays.asList(tut1, tut2, tut3));

        Iterable<Tutorial> tutorials = repository.findByTitleContaining("ring");
        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    void should_update_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        repository.saveAll(Arrays.asList(tut1, tut2));

        Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);

        Tutorial tut = repository.findById(tut2.getId()).get();
        tut.setTitle(updatedTut.getTitle());
        tut.setDescription(updatedTut.getDescription());
        tut.setPublished(updatedTut.getPublished());
        repository.save(tut);

        Tutorial checkTut = repository.findById(tut2.getId()).get();

        assertThat(checkTut.getId()).isEqualTo(tut2.getId());
        assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
        assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
        assertThat(checkTut.getPublished()).isEqualTo(updatedTut.getPublished());
    }

    @Test
    void should_delete_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        repository.saveAll(Arrays.asList(tut1, tut2,tut3));

        repository.deleteById(tut2.getId());

        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    void should_delete_all_tutorials() {
        repository.save(new Tutorial("Tut#1", "Desc#1", true));
        repository.save(new Tutorial("Tut#2", "Desc#2", false));
        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}
