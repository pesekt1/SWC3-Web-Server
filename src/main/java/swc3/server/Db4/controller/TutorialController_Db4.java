package swc3.server.Db4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Db4.models.Tutorial_db4;
import swc3.server.Db4.repo.TutorialRepository_db4;

import java.util.List;

@RestController
@RequestMapping("/db4")
public class TutorialController_Db4 {

    TutorialRepository_db4 tutorialRepository_db4;

    @Autowired
    public TutorialController_Db4(TutorialRepository_db4 tutorialRepository_db4){
        this.tutorialRepository_db4 = tutorialRepository_db4;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial_db4>> getAllTutorials() {
        List<Tutorial_db4> tutorials = tutorialRepository_db4.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


