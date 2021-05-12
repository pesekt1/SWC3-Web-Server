package swc3.server.Datasources.Db5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.Datasources.Db5.models.TutorialMongo;
import swc3.server.Datasources.Db5.repo.TutorialMongoRepository;
import swc3.server.PrimaryDatasource.models.Tutorial;

import java.util.List;

@Service
public class TutorialMongoService {
    TutorialMongoRepository tutorialMongoRepository;

    @Autowired
    public TutorialMongoService(TutorialMongoRepository tutorialRepository){
        this.tutorialMongoRepository = tutorialRepository;
    }

    public ResponseEntity<List<TutorialMongo>> getAll() {
        List<TutorialMongo> tutorials = tutorialMongoRepository.findAll();

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    public ResponseEntity<TutorialMongo> createTutorial(TutorialMongo tutorial) {
        TutorialMongo newTutorial = tutorialMongoRepository
                .save(new TutorialMongo(tutorial.getTitle(),tutorial.getDescription(), false));
        return new ResponseEntity<>(newTutorial, HttpStatus.CREATED);
    }
}
