package swc3.server.Datasources.Db2.JDBC.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import swc3.server.Datasources.Db2.JDBC.Model.Course;

import java.util.Optional;

//This class is showing different jdbc features
@Component
public class ExtraCourseDAO {

    private static final Logger log = LoggerFactory.getLogger(CourseDAO.class);

    //here we select the data source JdbcTemplate
    @Qualifier("jdbcTemplateDb2")
    private final JdbcTemplate jdbcTemplate;

    @Qualifier("NamedParameterJdbcTemplateDb2")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ExtraCourseDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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



}
