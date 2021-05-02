package swc3.server.Datasources.Db6.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db6.models.Tutorial_sqlite;

import java.util.List;

@RepositoryRestResource(path = "tutorialsSQLite")
public interface TutorialRepository_sqlite extends JpaRepository<Tutorial_sqlite, Short> {
    List<Tutorial_sqlite> findByPublished(boolean published);
    List<Tutorial_sqlite> findByTitleContaining(String title);
}