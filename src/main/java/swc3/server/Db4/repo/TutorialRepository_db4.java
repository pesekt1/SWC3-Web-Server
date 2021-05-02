package swc3.server.Db4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.Db4.models.Tutorial_db4;

@RepositoryRestResource(path = "tutorialsDb4") //overriding the auto-generated api path
public interface TutorialRepository_db4 extends JpaRepository<Tutorial_db4, Integer> {
}