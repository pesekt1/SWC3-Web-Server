package swc3.server.Datasources.Db5.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.Datasources.Db5.models.TutorialMongo;
import swc3.server.Datasources.Db5.repo.TutorialMongoRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class TutorialMongoService {
    TutorialMongoRepository tutorialMongoRepository;

    @Autowired
    public TutorialMongoService(TutorialMongoRepository tutorialRepository){
        this.tutorialMongoRepository = tutorialRepository;
    }

    private String errorMessage(ObjectId id){
        return "Not found Tutorial with id = " + id;
    }

    public ResponseEntity<TutorialMongo> getById(ObjectId id) {
        var tutorial = tutorialMongoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id))); //this will be caught by our ControllerExceptionHandler

        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    public ResponseEntity<List<TutorialMongo>> getAll() {
        var tutorials = tutorialMongoRepository.findAll();

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    public ResponseEntity<TutorialMongo> create(TutorialMongo tutorial) {
        var newTutorial = tutorialMongoRepository
                .save(new TutorialMongo(tutorial.getTitle(),tutorial.getDescription(), tutorial.getPublished()));
        return new ResponseEntity<>(newTutorial, HttpStatus.CREATED);
    }

    public ResponseEntity<TutorialMongo> update(ObjectId id, TutorialMongo tutorial) {
        var updatedTutorial = tutorialMongoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));

        updatedTutorial.setTitle(tutorial.getTitle());
        updatedTutorial.setDescription(tutorial.getDescription());
        updatedTutorial.setPublished(tutorial.getPublished());

        return new ResponseEntity<>(tutorialMongoRepository.save(updatedTutorial), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteById(ObjectId id) {
        if (!tutorialMongoRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));

        tutorialMongoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //status could be also 200 or 202
        //we could return the deleted object
    }

    public ResponseEntity<HttpStatus> deleteAll() {
        tutorialMongoRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
