package swc3.server.Datasources.Db2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Datasources.Db2.models.Tutorial_db2;
import swc3.server.Datasources.Db2.repo.TutorialRepository_db2;

import java.util.List;

@RestController
@RequestMapping("/db2")
public class TutorialController_Db2 {

    TutorialRepository_db2 tutorialRepository_db2;

    @Autowired
    public TutorialController_Db2(TutorialRepository_db2 tutorialRepository_db2){
        this.tutorialRepository_db2 = tutorialRepository_db2;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial_db2>> getAllTutorials() {
        List<Tutorial_db2> tutorials = tutorialRepository_db2.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


