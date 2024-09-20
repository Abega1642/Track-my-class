package dev.razafindratelo.trackmyclass.services.courseServices;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    Course getCourseById(String courseId);

    Course getCourseByName(String name);
}
