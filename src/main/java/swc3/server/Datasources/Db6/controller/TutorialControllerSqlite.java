package swc3.server.Datasources.Db6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Datasources.Db6.models.TutorialSqlite;
import swc3.server.Datasources.Db6.repo.TutorialRepositorySqlite;

import java.util.List;

@RestController
@RequestMapping("/sqlite")
public class TutorialControllerSqlite {

    TutorialRepositorySqlite tutorialRepository;

    @Autowired
    public TutorialControllerSqlite(TutorialRepositorySqlite tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialSqlite>> getAllTutorials() {
        List<TutorialSqlite> tutorials = tutorialRepository.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


