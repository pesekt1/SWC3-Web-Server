package swc3.server.Db6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Db6.models.Tutorial_sqlite;
import swc3.server.Db6.repo.TutorialRepository_sqlite;

import java.util.List;

@RestController
@RequestMapping("/sqlite")
public class TutorialController_sqlite {

    TutorialRepository_sqlite tutorialRepository;

    @Autowired
    public TutorialController_sqlite(TutorialRepository_sqlite tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial_sqlite>> getAllTutorials() {
        List<Tutorial_sqlite> tutorials = tutorialRepository.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}


