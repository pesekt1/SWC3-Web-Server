package swc3.server.PrimaryDatasource.services.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.repository.TutorialRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService{
    TutorialRepository tutorialRepository;

//    @PersistenceContext(name = "entityManagerFactory")
//    EntityManager em;

    @Autowired
    public TutorialServiceImpl(TutorialRepository tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    private String errorMessage(long id){
        return "Not found Tutorial with id = " + id;
    }

    @Override
    public ResponseEntity<Tutorial> getById(long id) {
        var tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id))); //this will be caught by our ControllerExceptionHandler

        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Tutorial>> getAll(String title) {
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

    @Override
    public ResponseEntity<Tutorial> create(Tutorial tutorial) {
        var newTutorial = tutorialRepository
                .save(new Tutorial(tutorial.getTitle(),tutorial.getDescription(), false));
        return new ResponseEntity<>(newTutorial, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Tutorial> update(long id, Tutorial tutorial) {
        var updatedTutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));

        updatedTutorial.setTitle(tutorial.getTitle());
        updatedTutorial.setDescription(tutorial.getDescription());
        updatedTutorial.setPublished(tutorial.getPublished());

        return new ResponseEntity<>(tutorialRepository.save(updatedTutorial), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(long id) {
        if (!tutorialRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));

        tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //status could be also 200 or 202
        //we could return the deleted object
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAll() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Tutorial>> findByPublished(boolean published) {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(published);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

//trying to create a vulnerable query
//    public ResponseEntity<List<Tutorial>> getAllTutorialsVulnerable(String title) {
//        List<Tutorial> tutorials = new ArrayList<>();
//        //using String concatenation, but it is still safe because Hibernate does not allow ";"
//        TypedQuery<Tutorial> query = em.createQuery(
//                "SELECT t FROM Tutorial t WHERE t.title = '" + title + "' AND t.published = true", Tutorial.class);
//
//        tutorials.addAll(query.getResultList());
//
//        if (tutorials.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(tutorials, HttpStatus.OK);
//    }
}