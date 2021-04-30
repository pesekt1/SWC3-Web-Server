package swc3.server;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

import swc3.server.Db2.models.Tutorial_db2;
import swc3.server.Db2.repo.TutorialRepository_db2;
import swc3.server.Db3.models.Tutorial_db3;
import swc3.server.Db3.repo.TutorialRepository_db3;
import swc3.server.config.Db1Config;
import swc3.server.config.Db2Config;
import swc3.server.models.Customer;
import swc3.server.models.Tutorial;
import swc3.server.repository.CustomerRepository;
import swc3.server.repository.TutorialRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

// integration tests for persistence unit db1 - first data source,
// transactionManager name comes from Db1Config class.

@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {Db1Config.class, TutorialRepository.class})
@Transactional("transactionManager")
class PrimaryDatasourceIntegrationTests {

    //repo for tutorial table from db1
    @Autowired
    private TutorialRepository repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
//
//    @Test
//    void should_store_a_tutorial() {
//        Tutorial tutorial = repository.save(new Tutorial("Tut title", "Tut desc", true));
//        assertThat(tutorial)
//            .hasFieldOrPropertyWithValue("title", "Tut title")
//            .hasFieldOrPropertyWithValue("description", "Tut desc")
//            .hasFieldOrPropertyWithValue("published", true);
//    }
//
//    @Test
//    void should_find_all_tutorials() {
//        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
//
//        repository.saveAll(Arrays.asList(tut1, tut2, tut3));
//        assertThat(repository.findAll()).hasSize(3).contains(tut1, tut2, tut3);
//    }
//
//    @Test
//    void should_find_tutorial_by_id() {
//        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//        repository.saveAll(Arrays.asList(tut1, tut2));
//
//        Tutorial foundTutorial = repository.findById(tut2.getId()).get();
//        assertThat(foundTutorial).isEqualTo(tut2);
//    }
//
//    @Test
//    void should_find_published_tutorials() {
//        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
//        repository.saveAll(Arrays.asList(tut1, tut2, tut3));
//
//        Iterable<Tutorial> tutorials = repository.findByPublished(true);
//        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//    }
//
//    @Test
//    void should_find_tutorials_by_title_containing_string() {
//        Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
//        Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
//        Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
//        repository.saveAll(Arrays.asList(tut1, tut2, tut3));
//
//        Iterable<Tutorial> tutorials = repository.findByTitleContaining("ring");
//        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//    }
//
//    @Test
//    void should_update_tutorial_by_id() {
//        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//        repository.saveAll(Arrays.asList(tut1, tut2));
//
//        Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);
//
//        Tutorial tut = repository.findById(tut2.getId()).get();
//        tut.setTitle(updatedTut.getTitle());
//        tut.setDescription(updatedTut.getDescription());
//        tut.setPublished(updatedTut.getPublished());
//        repository.save(tut);
//
//        Tutorial checkTut = repository.findById(tut2.getId()).get();
//
//        assertThat(checkTut.getId()).isEqualTo(tut2.getId());
//        assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
//        assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
//        assertThat(checkTut.getPublished()).isEqualTo(updatedTut.getPublished());
//    }
//
//    @Test
//    void should_delete_tutorial_by_id() {
//        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
//        repository.saveAll(Arrays.asList(tut1, tut2,tut3));
//
//        repository.deleteById(tut2.getId());
//
//        Iterable<Tutorial> tutorials = repository.findAll();
//        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//    }
//
//    @Test
//    void should_delete_all_tutorials() {
//        repository.save(new Tutorial("Tut#1", "Desc#1", true));
//        repository.save(new Tutorial("Tut#2", "Desc#2", false));
//        repository.deleteAll();
//
//        assertThat(repository.findAll()).isEmpty();
//    }
}