package dev.razafindratelo.trackmyclass.services.courseServices;

import dev.razafindratelo.trackmyclass.dao.CourseDAO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.mergers.CourseMerger;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceDuplicatedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;
    private final CourseMerger courseMerger;


    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    @Override
    public Course getCourseByName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalRequestException("Course name cannot be null or empty");
        } else {
            return courseDAO.getCourseByName(name);
        }
    }

    @Override
    public Level getCourseLevelYear(String courseName) {
        if(courseName == null || courseName.isEmpty()) {
            throw new IllegalRequestException("Course name cannot be null or empty");
        }
        Course course = courseDAO.getCourseByName(courseName);
        if(course == null) {
            throw new IllegalRequestException("Course " + courseName + " not found");
        }
        return Level.valueOf(course.getCourseRef().substring(0,2));
    }

    @Override
    public String courseRefGenerator(String level) {
        LocalDateTime now = LocalDateTime.now();

        if (getAllCourses().isEmpty()) {
            return level.toUpperCase()+"-001";
        } else {
            String upperCasedLevel = level.toUpperCase();

            List<String> courseRefs = getAllCourses()
                    .stream()
                    .filter(crs -> getCourseLevelYear(crs.getName()).equals(Level.valueOf(upperCasedLevel)))
                    .map(Course::getCourseRef)
                    .sorted()
                    .toList();

            if(courseRefs.isEmpty()) {
                return level.toUpperCase()+"-001";
            } else {
                String courseRef = courseRefs.getLast();
                int number = Integer.parseInt(courseRef.substring(3, 6)) + 1;

                return (number < 10) ? courseRef.substring(0, 3) +"00"+ number : courseRef.substring(0, 3) +"0"+ number;
            }

        }
    }

    @Override
    public Course addCourse(Course course, String correspondingLevel) {
        if(course == null)
            throw new IllegalRequestException("Course cannot be null");

        if (getCourseByName(course.getName()) != null)
            throw new ResourceDuplicatedException("Course already exists (similar name)");

        if (course.getName() == null || course.getName().isEmpty())
            throw new IllegalRequestException("Course name cannot be null or empty");


        if (course.getCourseRef() == null || course.getCourseRef().length() != 8
            || course.getCourseRef().substring(0, 3).equalsIgnoreCase(correspondingLevel + "-"))
            course.setCourseRef(courseRefGenerator(correspondingLevel));

        if (course.getCredit() < 0 )
            course.setCredit(0);

        return courseDAO.addCourse(course);

    }

    @Override
    public Course updateCourse(String courseName, Course course) {

        Course courseToUpdate = getAllCourses()
                .stream().filter(crs -> crs.getCourseRef().equalsIgnoreCase(courseName)).toList().getFirst();

        if (courseToUpdate == null)
            throw new IllegalRequestException("Course " + courseName + " not found");

        if (course.getName() == null || course.getName().isEmpty())
            throw new IllegalRequestException("Course name cannot be null or empty");

        return courseDAO.updateCourse(courseToUpdate.getCourseRef(), course);
    }

    @Override
    public Course updateCoursePartially(String courseName, Course course) throws NoSuchFieldException, IllegalAccessException {
        Course courseToUpdate = getAllCourses()
                .stream().filter(crs -> crs.getCourseRef().equalsIgnoreCase(courseName)).toList().getFirst();
        if (courseToUpdate == null)
            throw new IllegalRequestException("Course " + courseName + " not found");

        courseMerger.mergeFields(course, courseToUpdate);
        return courseDAO.updateCourse(courseToUpdate.getCourseRef(), courseToUpdate);
    }

    @Override
    public Course deleteCourse(String courseName) {
        Course courseToDelete = getCourseByName(courseName);
        return courseDAO.deleteCourse(courseToDelete);
    }

}
