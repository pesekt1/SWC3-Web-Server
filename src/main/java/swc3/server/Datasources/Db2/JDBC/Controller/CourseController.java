package swc3.server.Datasources.Db2.JDBC.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import swc3.server.Datasources.Db2.JDBC.DAO.DAO;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController implements CourseOperations{

    private final DAO<Course> dao;

    @Autowired
    public CourseController(@Qualifier("courseDAO") DAO<Course> dao) { // Qualifier - select the implementation class
        this.dao = dao;
    }

    //for showing an sql injection vulnerability
    @GetMapping("/vulnerable")
    public List<Course> getAllVulnerable(@RequestParam String filter) {
        return dao.getAllVulnerable(filter);
    }

    @Override
    public List<Course> getAll() {
        return dao.getAll();
    }

    @Override
    public Course getById(int id) {
        Optional<Course> course = dao.getById(id);
        if(course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course Not Found.");
        }
        return course.get();
    }

    @Override
    public void create(Course course) {
        dao.create(course);
    }

    @Override
    public void update(Course course, int id) {
        dao.update(course,id);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}