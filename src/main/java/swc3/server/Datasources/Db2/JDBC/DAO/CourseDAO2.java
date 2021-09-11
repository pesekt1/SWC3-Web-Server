package swc3.server.Datasources.Db2.JDBC.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

import java.util.List;
import java.util.Optional;

// another implementing class of DAO interface -
// just to show that now in the CourseController we need to use Qualifier
// to specify which implementation should be injected

@Component
public class CourseDAO2 implements DAO<Course> {

    private static final Logger log = LoggerFactory.getLogger(CourseDAO2.class);
    private final JdbcTemplate jdbcTemplate;

    public CourseDAO2(@Qualifier("jdbcTemplateDb2") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public List<Course> getAllVulnerable(String filter) {
        return null;
    }

    @Override
    public void create(Course course) {

    }

    @Override
    public Optional<Course> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Course course, int id) {

    }

    @Override
    public void delete(int id) {

    }
}