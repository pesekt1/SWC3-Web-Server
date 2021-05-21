package swc3.server.Datasources.Db2.JDBC.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This class is showing different jdbc features
@Component

public class ExtraCourseDAO {
    private static final Logger log = LoggerFactory.getLogger(ExtraCourseDAO.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public ExtraCourseDAO(@Qualifier("jdbcTemplateDb2") JdbcTemplate jdbcTemplate,
                          @Qualifier("NamedParameterJdbcTemplateDb2") NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          @Qualifier("dataSourceDb2") DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.dataSource = dataSource;
    }

    public int getCount(){
        String sql = "SELECT COUNT(*) FROM courses";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return (result == null) ? 0 : result; //there could be nullPointerException while unboxing int(Integer).
    }

    public Course getById(int id){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        String sql = "SELECT * FROM courses WHERE course_id = :id";
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new CourseRowMapper());
    }

    public int create(Course course) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", course.getTitle());
        parameters.put("description", course.getDescription());
        parameters.put("link", course.getLink());

        var simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("courses");
        return simpleJdbcInsert.execute(parameters);
    }

    public Course getByIdSP(int id){
        var simpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_get_course_by_id");
        SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
        Map<String, Object> out = simpleJdbcCall.execute(in);

        Course course = new Course();
        course.setDescription((String)out.get("description"));
        course.setCourseId(id);
        course.setLink((String)out.get("link"));
        course.setTitle((String)out.get("title"));

        return course;
    }

    public Course getByIdSP2(int id){
        String sql = "CALL SP_get_course_by_id2(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CourseRowMapper());
    }

    public List<Course> getAllSP(){
        String sql = "CALL SP_get_all_courses()";
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }

    //Java 8
    public List<Course> getAllSPWithBeanMapper(){
        String sql = "SELECT * FROM courses";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
    }

    //Java 11
    public List<Course> getAllSPFunctional() {
        String sql = "SELECT * FROM courses";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> //this is like the mapRow method from RowMapper
                        new Course(
                                rs.getInt("course_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getString("link")
                        )
        );
    }


    public List<Course> getAllSPForList() {
        String sql = "SELECT * FROM courses";

        List<Course> courses = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            Course course = new Course();

            course.setCourseId( (int) (row.get("course_id")));
            course.setTitle( (String)row.get("title"));
            course.setLink(( (String)row.get("link")));
            course.setDescription(( (String)row.get("description")));

            courses.add(course);
        }

        return courses;
    }
}
