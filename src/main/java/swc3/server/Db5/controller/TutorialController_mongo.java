package swc3.server.Db5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Db5.models.Tutorial_mongo;
import swc3.server.Db5.repo.TutorialRepository_mongo;

import java.util.List;

@RestController
@RequestMapping("/mongo")
public class TutorialController_mongo {

    TutorialRepository_mongo tutorialRepository;

    @Autowired
    public TutorialController_mongo(TutorialRepository_mongo tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial_mongo>> getAllTutorials() {
        List<Tutorial_mongo> tutorials = tutorialRepository.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


