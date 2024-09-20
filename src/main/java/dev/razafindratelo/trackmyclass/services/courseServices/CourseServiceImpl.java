package dev.razafindratelo.trackmyclass.services.courseServices;

import dev.razafindratelo.trackmyclass.dao.CourseDAO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;

    @Override
    public Course getCourseById(String courseId) {
        if(courseId == null || courseId.isEmpty()) {
            throw new IllegalRequestException("Course ID cannot be null or empty");
        } else {
            return courseDAO.getCourseByRef(courseId);
        }
    }

    @Override
    public Course getCourseByName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalRequestException("Course name cannot be null or empty");
        } else {
            return courseDAO.getCourseByName(name);
        }
    }
}
