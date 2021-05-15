package swc3.server.Datasources.Db4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Datasources.Db4.models.TutorialDb4;

@RepositoryRestResource(path = "tutorialsDb4") //overriding the auto-generated api path
public interface TutorialRepositoryDb4 extends JpaRepository<TutorialDb4, Integer> {
}