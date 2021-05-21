package swc3.server.Datasources.Db2.JDBC.Controller;

import org.springframework.web.bind.annotation.*;
import swc3.server.Datasources.Db2.JDBC.DAO.CourseRowMapper;
import swc3.server.Datasources.Db2.JDBC.DAO.ExtraCourseDAO;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courses-extra")
public class ExtraCourseController {
    ExtraCourseDAO extraDao;

    public ExtraCourseController(ExtraCourseDAO extraDao) {
        this.extraDao = extraDao;
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable int id){
        return extraDao.getById(id);
    }

    @GetMapping("/SP{id}")
    public Course getByIdSP(@PathVariable int id){
        return extraDao.getByIdSP(id);
    }

    @GetMapping("/SP2{id}")
    public Course getByIdSP2(@PathVariable int id){
        return extraDao.getByIdSP2(id);
    }

    @GetMapping
    public List<Course> getAll(){
        return extraDao.getAllSP();
    }

    @GetMapping("/bean-mapper")
    public List<Course> getAllWithBeanMapper(){
        return extraDao.getAllSPWithBeanMapper();
    }

    @GetMapping("/functional")
    public List<Course> getAllFunctional(){
        return extraDao.getAllSPFunctional();
    }

    @GetMapping("/query-for-list")
    public List<Course> getAllForList(){
        return extraDao.getAllSPForList();
    }

    @GetMapping("/count")
    public int getCount(){
        return extraDao.getCount();
    }

    @PostMapping
    public int createCourse(@Valid @RequestBody Course course){ //@Valid will validate properties
        return extraDao.create(course);
    }
}
