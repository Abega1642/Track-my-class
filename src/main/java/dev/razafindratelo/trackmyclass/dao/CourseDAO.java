package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import dev.razafindratelo.trackmyclass.mapper.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class CourseDAO {
    private DBConnection dbConnection;

    public Course getCourseByName(String name) {
        try {
            PreparedStatement getCourse = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                SELECT 
                                    crs_ref,
                                    name crs_name,
                                    credit crs_credit
                                FROM course WHERE name ILIKE ?
                                """
                    );
            getCourse.setString(1, name);
            ResultSet resultSet = getCourse.executeQuery();
            if(resultSet.next()) {
                return CourseMapper.mapToCourse(resultSet);
            }

        } catch (SQLException e) {
            throw new InternalException("Error while getting course by name: " + e.getMessage());
        }
        return null;
    }

    public Course getCourseByRef(String courseRef) {
        try {
            PreparedStatement getCourse = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                SELECT 
                                    crs_ref,
                                    name crs_name,
                                    credit crs_credit
                                FROM course WHERE crs_ref = ?
                                """
                    );
            getCourse.setString(1, courseRef);
            ResultSet resultSet = getCourse.executeQuery();
            if(resultSet.next()) {
                return CourseMapper.mapToCourse(resultSet);
            }

        } catch (SQLException e) {
            throw new InternalException("Error while getting course by name: " + e.getMessage());
        }
        return null;
    }
}
