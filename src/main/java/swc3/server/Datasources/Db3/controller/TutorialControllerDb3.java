package swc3.server.Datasources.Db3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Datasources.Db3.models.TutorialDb3;
import swc3.server.Datasources.Db3.repo.TutorialRepositoryDb3;

import java.util.List;

@RestController
@RequestMapping("/db3")
public class TutorialControllerDb3 {

    TutorialRepositoryDb3 tutorialRepositoryDb3;

    @Autowired
    public TutorialControllerDb3(TutorialRepositoryDb3 tutorialRepositoryDb3){
        this.tutorialRepositoryDb3 = tutorialRepositoryDb3;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDb3>> getAllTutorials() {
        List<TutorialDb3> tutorials = tutorialRepositoryDb3.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


