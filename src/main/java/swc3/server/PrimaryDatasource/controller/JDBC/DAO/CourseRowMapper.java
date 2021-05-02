package swc3.server.PrimaryDatasource.controller.JDBC.DAO;

import org.springframework.jdbc.core.RowMapper;

import swc3.server.PrimaryDatasource.controller.JDBC.Model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

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