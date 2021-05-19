package swc3.server.Datasources.Db2.JDBC.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

import javax.validation.Valid;
import java.util.List;

public interface CourseOperations {
    @GetMapping
    List<Course> getAll();

    @GetMapping("/{id}")
    Course getById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody Course course);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody Course course, @PathVariable int id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
