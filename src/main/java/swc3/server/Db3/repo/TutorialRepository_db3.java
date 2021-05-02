package swc3.server.Db3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Db3.models.Tutorial_db3;

@RepositoryRestResource(path = "tutorialsDb3") //overriding the auto-generated api path
public interface TutorialRepository_db3 extends JpaRepository<Tutorial_db3, Integer> {
}