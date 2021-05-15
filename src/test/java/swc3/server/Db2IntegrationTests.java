package swc3.server;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import swc3.server.Datasources.Db2.models.Tutorial_db2;
import swc3.server.Datasources.Db2.repo.TutorialRepository_db2;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.config.Db2Config;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

// integration tests for persistence unit db2,

@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {TutorialRepository_db2.class})
@ComponentScan(resourcePattern = "**/Db2Config.class")
@Transactional(transactionManager = "transactionManagerDb2")
class Db2IntegrationTests {

    //repo for tutorial table from db2
    @Autowired
    private TutorialRepository_db2 repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should not find any tutorials if repository is empty")
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial_db2> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    @DisplayName("Should store a tutorial")
    void should_store_a_tutorial() {
        //we should not do it like this - to pass a tutorial object into save method because now the objects are connected:
//        var newTutorial = new Tutorial_db2("Tut title", "Tut desc", true);
//        var savedTutorial = repository.save(newTutorial); // save method returns the saved object
//        Assertions.assertThat(savedTutorial).isEqualTo(newTutorial);

        var tutorial = repository.save(new Tutorial_db2("Tut title", "Tut desc", true));
        assertThat(tutorial)
                .hasFieldOrPropertyWithValue("title", "Tut title")
                .hasFieldOrPropertyWithValue("description", "Tut desc")
                .hasFieldOrPropertyWithValue("published", true)
                .hasFieldOrProperty("id").isNotNull();
    }

    @Test
    void should_find_all_tutorials() {
        //not a good way to test it - saveAll() is affecting the objects... it should be decoupled
        var tut1 = new Tutorial_db2("Tut#1", "Desc#1", true);
        var tut2 = new Tutorial_db2("Tut#2", "Desc#2", false);
        var tut3 = new Tutorial_db2("Tut#3", "Desc#3", true);

        repository.saveAll(Arrays.asList(tut1, tut2, tut3));
        assertThat(repository.findAll()).hasSize(3).contains(tut1, tut2, tut3);
    }
}
