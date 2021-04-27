package swc3.server.controller.JDBC.DAO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import swc3.server.controller.JDBC.Model.Course;

import java.util.List;
import java.util.Optional;

@Component
public class CourseDAO implements DAO<Course> {

    private static final Logger log = LoggerFactory.getLogger(CourseDAO.class);
    private JdbcTemplate jdbcTemplate;

    public CourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> list() {
        String sql = "SELECT course_id,title,description,link from courses";
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }

    //This endpoint is vulnerable, it allows SQL injection
    //use this as an argument: "http://google.com" OR 1 = 1
    @Override
    public List<Course> listVulnerable(String filter) {
        String sql = "SELECT course_id,title,description,link from courses WHERE link =" + filter;
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }

    @Override
    public void create(Course course) {
        String sql = "insert into courses(title,description,link) values(?,?,?)";
        int insert = jdbcTemplate.update(sql,course.getTitle(),course.getDescription(),course.getLink());
        if(insert == 1) {
            log.info("New Course Created: " + course.getTitle());
        }
    }

    @Override
    public Optional<Course> get(int id) throws DataAccessException {
        String sql = "SELECT course_id,title,description,link from courses where course_id = ?";
        Course course = null;
        try {
            course = jdbcTemplate.queryForObject(sql, new Object[]{id}, new CourseRowMapper());
        }catch (DataAccessException ex) {
            log.info("Course not found: " + id);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public void update(Course course, int id) {
        String sql = "update courses set title = ?, description = ?, link = ? where course_id = ?";
        int update = jdbcTemplate.update(sql,course.getTitle(),course.getDescription(),course.getLink(),id);
        if(update == 1) {
            log.info("Course Updated: " + course.getTitle());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from courses where course_id = ?";
        int delete = jdbcTemplate.update(sql,id);
        if(delete == 1) {
            log.info("Course Deleted: " + id);
        }
    }

}