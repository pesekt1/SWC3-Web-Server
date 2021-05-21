package swc3.server.Datasources.Db2.JDBC.DAO;

import org.springframework.jdbc.core.RowMapper;

import swc3.server.Datasources.Db2.JDBC.Model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("course_id"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        course.setLink(resultSet.getString("link"));
        return course;
    }
}