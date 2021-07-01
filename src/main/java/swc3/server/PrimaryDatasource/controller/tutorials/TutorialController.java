package swc3.server.PrimaryDatasource.controller.tutorials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO introduce DTO - data transfer object, so that controller does not know the Tutorial model class.
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.services.tutorial.TutorialService;

@RestController
@RequestMapping("/api3")
public class TutorialController {
	TutorialService tutorialService;

	@Autowired
	public TutorialController(@Qualifier("tutorialServiceImpl") TutorialService tutorialService){
		this.tutorialService = tutorialService;
	}

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		return tutorialService.getAll(title);
	}

	//testing if we can create a vulnerable endpoint
	//testing sql injection: Hibernate does not allow ";" in the query so it is not possible to send multiple statements.
	// for example if we try: http://localhost:5557/api3/tutorialsVulnerable?title=title4'; -- +
	// it does not work: org.hibernate.QueryException: unexpected char: ';'

//	@GetMapping("/tutorialsVulnerable")
//	public ResponseEntity<List<Tutorial>> getAllTutorialsVulnerable(@RequestParam String title) {
//		return tutorialService.getAllTutorialsVulnerable(title);
//	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		return tutorialService.getById(id);
	}

	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		return tutorialService.create(tutorial);
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		return tutorialService.update(id, tutorial);
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		return tutorialService.delete(id);
	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		return tutorialService.deleteAll();
	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished(@RequestBody boolean published) {
		return tutorialService.findByPublished(published);
	}

}


