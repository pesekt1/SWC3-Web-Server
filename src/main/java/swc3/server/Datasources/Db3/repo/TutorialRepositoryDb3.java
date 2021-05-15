package swc3.server.Datasources.Db3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db3.models.TutorialDb3;

@RepositoryRestResource(path = "tutorialsDb3") //overriding the auto-generated api path
public interface TutorialRepositoryDb3 extends JpaRepository<TutorialDb3, Integer> {
}