package swc3.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialService {
    TutorialRepository tutorialRepository;
    EntityManager em;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository, EntityManager em){
        this.tutorialRepository = tutorialRepository;
        this.em = em;
    }

    public ResponseEntity<Tutorial> getTutorialById(long id) {
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        //this will be caught by our ControllerExceptionHandler
        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    public ResponseEntity<List<Tutorial>> getAllTutorials(String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null)
            tutorials.addAll(tutorialRepository.findAll());
        else
            tutorials.addAll(tutorialRepository.findByTitleContaining(title));

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    //trying to create a vulnerable query
    public ResponseEntity<List<Tutorial>> getAllTutorialsVulnerable(String title) {
        List<Tutorial> tutorials = new ArrayList<>();
        //using String concatenation, but it is still safe because Hibernate does not allow ";"
        TypedQuery<Tutorial> query = em.createQuery(
                "SELECT t FROM Tutorial t WHERE t.title = '" + title + "' AND t.published = true", Tutorial.class);

        tutorials.addAll(query.getResultList());

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    public ResponseEntity<Tutorial> createTutorial(Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository
                .save(new Tutorial(tutorial.getTitle(),tutorial.getDescription(), false));
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    }

    public ResponseEntity<Tutorial> updateTutorial(long id, Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.getPublished());

        return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteTutorial(long id) {
        tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}