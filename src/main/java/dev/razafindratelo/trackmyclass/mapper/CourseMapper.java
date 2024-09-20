package dev.razafindratelo.trackmyclass.mapper;

import dev.razafindratelo.trackmyclass.entity.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper {
    public static Course mapToCourse(ResultSet resultSet) throws SQLException {
        return new Course(
                resultSet.getString("crs_ref"),
                resultSet.getString("course_name"),
                resultSet.getInt("crs_credit")
        );
    }
}
