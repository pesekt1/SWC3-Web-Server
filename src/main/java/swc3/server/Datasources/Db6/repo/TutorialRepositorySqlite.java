package swc3.server.Datasources.Db6.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db6.models.TutorialSqlite;

import java.util.List;

@RepositoryRestResource(path = "tutorialsSQLite")
public interface TutorialRepositorySqlite extends JpaRepository<TutorialSqlite, Short> {
    List<TutorialSqlite> findByPublished(int published);
    List<TutorialSqlite> findByTitleContaining(String title);
}