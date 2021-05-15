package swc3.server.Datasources.Db4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Datasources.Db4.models.TutorialDb4;
import swc3.server.Datasources.Db4.repo.TutorialRepositoryDb4;

import java.util.List;

@RestController
@RequestMapping("/db4")
public class TutorialControllerDb4 {

    TutorialRepositoryDb4 tutorialRepositoryDb4;

    @Autowired
    public TutorialControllerDb4(TutorialRepositoryDb4 tutorialRepositoryDb4){
        this.tutorialRepositoryDb4 = tutorialRepositoryDb4;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDb4>> getAllTutorials() {
        List<TutorialDb4> tutorials = tutorialRepositoryDb4.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


