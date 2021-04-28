package swc3.server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import swc3.server.config.Db1Config;
import swc3.server.config.Db2Config;
import swc3.server.config.Db3Config;
import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestEntityManager
@SpringBootTest(classes = {ServerApplication.class, Db1Config.class, Db2Config.class, Db3Config.class})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTests {

  //@Autowired
  //private TestEntityManager entityManager;

  @Autowired
  TutorialRepository repository;

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
  public void should_store_a_tutorial() {
    Tutorial tutorial = repository.save(new Tutorial("Tut title", "Tut desc", true));
    assertThat(tutorial).hasFieldOrPropertyWithValue("title", "Tut title");
    assertThat(tutorial).hasFieldOrPropertyWithValue("description", "Tut desc");
    assertThat(tutorial).hasFieldOrPropertyWithValue("published", true);
  }

//  @Test
//  public void should_find_all_tutorials() {
//    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//
//    entityManagerFactory.createEntityManager().persist(tut1);
//    //entityManager.persist(tut1);
//
//    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//    entityManagerFactory.createEntityManager().persist(tut2);
//    //entityManager.persist(tut2);
//
//    Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
//    entityManagerFactory.createEntityManager().persist(tut3);
//    //entityManager.persist(tut3);
//
//    Iterable<Tutorial> tutorials = repository.findAll();
//
//    assertThat(tutorials).hasSize(3).contains(tut1, tut2, tut3);
//  }
//
//  @Test
//  public void should_find_tutorial_by_id() {
//    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Tutorial foundTutorial = repository.findById(tut2.getId()).get();
//
//    assertThat(foundTutorial).isEqualTo(tut2);
//  }
//
//  @Test
//  public void should_find_published_tutorials() {
//    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    Iterable<Tutorial> tutorials = repository.findByPublished(true);
//
//    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//  }
//
//  @Test
//  public void should_find_tutorials_by_title_containing_string() {
//    Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    Iterable<Tutorial> tutorials = repository.findByTitleContaining("ring");
//
//    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//  }
//
//  @Test
//  public void should_update_tutorial_by_id() {
//    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);
//
//    Tutorial tut = repository.findById(tut2.getId()).get();
//    tut.setTitle(updatedTut.getTitle());
//    tut.setDescription(updatedTut.getDescription());
//    tut.setPublished(updatedTut.getPublished());
//    repository.save(tut);
//
//    Tutorial checkTut = repository.findById(tut2.getId()).get();
//
//    assertThat(checkTut.getId()).isEqualTo(tut2.getId());
//    assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
//    assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
//    assertThat(checkTut.getPublished()).isEqualTo(updatedTut.getPublished());
//  }
//
//  @Test
//  public void should_delete_tutorial_by_id() {
//    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    repository.deleteById(tut2.getId());
//
//    Iterable<Tutorial> tutorials = repository.findAll();
//
//    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//  }
//
//  @Test
//  public void should_delete_all_tutorials() {
//    entityManager.persist(new Tutorial("Tut#1", "Desc#1", true));
//    entityManager.persist(new Tutorial("Tut#2", "Desc#2", false));
//
//    repository.deleteAll();
//
//    assertThat(repository.findAll()).isEmpty();
//  }
}
