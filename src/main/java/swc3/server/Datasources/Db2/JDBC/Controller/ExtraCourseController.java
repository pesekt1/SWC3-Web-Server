package swc3.server.Datasources.Db2.JDBC.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.Datasources.Db2.JDBC.DAO.ExtraCourseDAO;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

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

    @GetMapping("/count")
    public int getCount(){
        return extraDao.getCount();
    }


}
