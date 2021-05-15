package swc3.server.Datasources.Db2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db2.models.TutorialDb2;

@RepositoryRestResource(path = "tutorialsDb2") //overriding the auto-generated api path
public interface TutorialRepositoryDb2 extends JpaRepository<TutorialDb2, Long> {
}