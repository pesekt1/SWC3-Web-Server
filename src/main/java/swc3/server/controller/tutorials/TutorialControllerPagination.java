package swc3.server.controller.tutorials;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import swc3.server.models.Tutorial;
import swc3.server.repository.TutorialRepository;


@CrossOrigin(origins = {"http://localhost:8081", "https://swc3-react-frontend.herokuapp.com"})
@RestController
@RequestMapping("/api3")
public class TutorialControllerPagination {

    @Autowired
    TutorialRepository tutorialRepository;

    //retrieve paginated tutorials
    @GetMapping("/tutorials")
    public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> pageTuts;
        if (title == null)
            pageTuts = tutorialRepository.findAll(paging);
        else
            pageTuts = tutorialRepository.findByTitleContaining(title, paging);

        List<Tutorial> tutorials = pageTuts.getContent();

        if (tutorials.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", tutorials);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //retrieve paginated tutorials which are published
    @GetMapping("/tutorials/published")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

            Pageable paging = PageRequest.of(page, size);

            Page<Tutorial> pageTuts = tutorialRepository.findByPublished(true, paging);
            List<Tutorial> tutorials = pageTuts.getContent();

            if (tutorials.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
    }

}