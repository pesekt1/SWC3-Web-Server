package swc3.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public ResponseEntity<Tutorial> get(long id) {
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        //this will be caught by our ControllerExceptionHandler
        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }
}
