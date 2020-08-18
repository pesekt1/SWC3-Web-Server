package swc3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.model.Tutorial;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);
}
