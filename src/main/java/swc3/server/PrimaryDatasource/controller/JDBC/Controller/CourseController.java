package swc3.server.PrimaryDatasource.controller.JDBC.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import swc3.server.PrimaryDatasource.controller.JDBC.DAO.DAO;
import swc3.server.PrimaryDatasource.controller.JDBC.Model.Course;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private DAO<Course> dao;

    public CourseController(DAO<Course> dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Course> list() {
        return dao.list();
    }

    @GetMapping("/vulnerable")
    public List<Course> listVulnerable(@RequestParam String filter) {
        return dao.listVulnerable(filter);
    }

    @GetMapping("/{id}")
    public Course get(@PathVariable int id) {
        Optional<Course> course = dao.get(id);
        if(!course.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course Not Found.");
        }
        return course.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Course course) {
        dao.create(course);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Course course, @PathVariable int id) {
        dao.update(course,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        dao.delete(id);
    }
}