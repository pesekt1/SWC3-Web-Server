package swc3.server.controller.tutorials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;

import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;
import swc3.server.services.TutorialService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


//REST Controller for client-side rendering
@CrossOrigin(origins = {"http://localhost:8081", "https://swc3-react-frontend.herokuapp.com"})
@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;

	@Autowired
	TutorialService tutorialService;

	@Autowired
	EntityManager em;

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();

			if (title == null)
				tutorials.addAll(tutorialRepository.findAll());
			else
				tutorials.addAll(tutorialRepository.findByTitleContaining(title));

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	//testing sql injection: Hibernate does not allow ";" in the query so it is not possible to send multiple statements.
	@GetMapping("/tutorialsVulnerable")
	public ResponseEntity<List<Tutorial>> getAllTutorialsVulnerable(@RequestParam String title) {
		List<Tutorial> tutorials = new ArrayList<>();

		//using String concatination, but it is still safe because Hibernate does not allow ";"
		TypedQuery<Tutorial> query = em.createQuery(
				"SELECT t FROM Tutorial t WHERE t.title = '" + title + "'", Tutorial.class);

		tutorials.addAll(query.getResultList());

		if (tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		//example of using a service instead of directly calling the repository
		return tutorialService.get(id);
	}

	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
			Tutorial _tutorial = tutorialRepository
					.save(new Tutorial(tutorial.getTitle(),tutorial.getDescription(), false));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		Tutorial _tutorial = tutorialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

		_tutorial.setTitle(tutorial.getTitle());
		_tutorial.setDescription(tutorial.getDescription());
		_tutorial.setPublished(tutorial.getPublished());

		return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		tutorialRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		tutorialRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished() {
		List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

		if (tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

}


