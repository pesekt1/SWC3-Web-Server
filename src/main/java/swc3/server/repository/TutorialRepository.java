package swc3.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swc3.server.model.Tutorial;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	//overloaded methods using Pageable parameter:
	Page<Tutorial> findByPublished(boolean published, Pageable pageable);
	Page<Tutorial> findByTitleContaining(String title, Pageable pageable);

	List<Tutorial> findByTitleContaining(String title, Sort sort);

	//native query
	@Query(value = "SELECT * FROM tutorials t WHERE t.id = ?1", nativeQuery = true)
	Tutorial findTutorialById(long id);

}
