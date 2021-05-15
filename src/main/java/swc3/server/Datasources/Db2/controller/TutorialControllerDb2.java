package swc3.server.Datasources.Db2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Datasources.Db2.models.TutorialDb2;
import swc3.server.Datasources.Db2.repo.TutorialRepositoryDb2;

import java.util.List;

@RestController
@RequestMapping("/db2")
public class TutorialControllerDb2 {

    TutorialRepositoryDb2 tutorialRepositoryDb2;

    @Autowired
    public TutorialControllerDb2(TutorialRepositoryDb2 tutorialRepositoryDb2){
        this.tutorialRepositoryDb2 = tutorialRepositoryDb2;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDb2>> getAllTutorials() {
        List<TutorialDb2> tutorials = tutorialRepositoryDb2.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


