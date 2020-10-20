package swc3.server.controller.tutorials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import swc3.server.model.Tutorial;
import swc3.server.repository.TutorialRepository;


@CrossOrigin(origins = {"http://localhost:8081", "https://swc3-react-frontend.herokuapp.com"})
@RestController
@RequestMapping("/api4")
public class TutorialControllerSorting {

    @Autowired
    TutorialRepository tutorialRepository;

    //method retrieving the Sort.Direction enum
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //retrieve all tutorials sorted
    @GetMapping("/tutorials-all-sorted")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(defaultValue = "id,desc") String[] sort) {
        List<Order> orders = new ArrayList<Order>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by(orders));
        if (tutorials.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    //retrieve filtered, paginated, sorted, and tutorials
    @GetMapping("/tutorials-with-sorting")
    public ResponseEntity<Map<String, Object>> getAllTutorialsPageSorting(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        List<Order> orders = new ArrayList<Order>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Tutorial> pageTuts;
        if (title == null)
            pageTuts = tutorialRepository.findAll(pagingSort);
        else
            pageTuts = tutorialRepository.findByTitleContaining(title, pagingSort);

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