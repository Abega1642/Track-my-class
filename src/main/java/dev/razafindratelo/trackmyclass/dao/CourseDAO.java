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
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CourseDAO {
    private DBConnection dbConnection;

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        try {
            PreparedStatement getAll = dbConnection
                    .getConnection()
                    .prepareStatement("""
                                                SELECT
                                                    crs_ref,
                                                    name course_name,
                                                    credit crs_credit
                                                FROM course
                                           """);
            getAll.execute();
            ResultSet resultSet = getAll.executeQuery();
            while(resultSet.next()) {
                Course course = CourseMapper.mapToCourse(resultSet);
                courses.add(course);
            }
            return courses;

        } catch (SQLException e) {
            throw new InternalException("Error while retrieving all courses := " + e.getMessage());
        }
    }

    public Course getCourseByName(String name) {
        try {
            PreparedStatement getCourse = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                SELECT
                                    crs_ref,
                                    name course_name,
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

    public Course updateCourse(String courseRef,Course course) {
        try {
            PreparedStatement update = dbConnection
                    .getConnection()
                    .prepareStatement("UPDATE course SET name=?, credit=? WHERE crs_ref ILIKE ?");

            update.setString(1, course.getName());
            update.setInt(2, course.getCredit());
            update.setString(3, courseRef);

            update.executeUpdate();

            return course;

        } catch (SQLException e) {
            throw new InternalException("Error while updating course := " + e.getMessage());
        }
    }

    public Course addCourse(Course course) {
        try {
            PreparedStatement insertion = dbConnection
                    .getConnection()
                    .prepareStatement("""
                                               INSERT INTO Course (crs_ref, name, credit) VALUES
                                               (?,?,?)
                                           """
                    );
            insertion.setString(1, course.getCourseRef());
            insertion.setString(2, course.getName());
            insertion.setInt(3, course.getCredit());

            insertion.executeUpdate();
            return course;
        } catch (SQLException e) {
            throw new InternalException(
                    "Error while adding course with id: " + course.getCourseRef() +" := " + e.getMessage()
            );
        }
    }

    public Course deleteCourse(Course course) {
        try {
            PreparedStatement deletion = dbConnection
                    .getConnection()
                    .prepareStatement("DELETE FROM course WHERE course.name ILIKE ?");
            deletion.setString(1, course.getName());

            deletion.executeUpdate();

            return course;

        } catch (SQLException e) {
            throw new InternalException("Error while deleting course := " + e.getMessage());
        }
    }
}
