package dev.razafindratelo.trackmyclass.services.courseServices;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    List<Course> getAllCourses();

    Course getCourseByName(String name);

    Level getCourseLevelYear(String courseName);

    String courseRefGenerator(String level);

    Course addCourse(Course course, String correspondingLevel);

    Course updateCourse(String courseName, Course course);

    Course updateCoursePartially(String courseName, Course course) throws NoSuchFieldException, IllegalAccessException;


    Course deleteCourse(String courseName);
}
