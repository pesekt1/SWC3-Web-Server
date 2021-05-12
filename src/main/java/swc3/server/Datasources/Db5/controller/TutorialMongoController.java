package swc3.server.Datasources.Db5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.Datasources.Db5.models.TutorialMongo;
import swc3.server.Datasources.Db5.services.TutorialMongoService;

import java.util.List;

@RestController
@RequestMapping("/mongo")
public class TutorialMongoController {

    TutorialMongoService tutorialMongoService;

    @Autowired
    public TutorialMongoController(TutorialMongoService tutorialMongoService){
        this.tutorialMongoService = tutorialMongoService;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialMongo>> getAllTutorials() {
        return tutorialMongoService.getAll();
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialMongo> createTutorial(@RequestBody TutorialMongo tutorial) {
        return tutorialMongoService.createTutorial(tutorial);
    }
}


