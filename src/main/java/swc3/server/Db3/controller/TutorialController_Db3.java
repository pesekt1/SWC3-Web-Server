package swc3.server.Db3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Db3.models.Tutorial_db3;
import swc3.server.Db3.repo.TutorialRepository_db3;

import java.util.List;

@RestController
@RequestMapping("/db3")
public class TutorialController_Db3 {

    TutorialRepository_db3 tutorialRepository_db3;

    @Autowired
    public TutorialController_Db3(TutorialRepository_db3 tutorialRepository_db3){
        this.tutorialRepository_db3 = tutorialRepository_db3;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial_db3>> getAllTutorials() {
        List<Tutorial_db3> tutorials = tutorialRepository_db3.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


