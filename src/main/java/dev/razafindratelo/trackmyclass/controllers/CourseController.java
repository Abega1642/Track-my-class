package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.dao.CourseDAO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseDAO courseDAO;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(this.courseService.getAllCourses());
    }

    @GetMapping("/course")
    public ResponseEntity<Course> getCourseByCourseName(@RequestParam("course_name") String courseName) {
        return ResponseEntity.ok(courseService.getCourseByName(courseName));
    }

    @PostMapping("course/add")
    public ResponseEntity<Course> addCourse(
            @RequestParam("level_year") String level,
            @RequestBody Course course
    ) {
        return new ResponseEntity<>(
                courseService.addCourse(course, level),
                HttpStatus.CREATED
        );
    }

    @PutMapping("course/update/{courseName}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable("courseName") String courseName,
            @RequestBody Course course
    ) {
        return ResponseEntity.ok(courseService.updateCourse(courseName, course));
    }

    @PatchMapping("course/update/{courseName}")
    public ResponseEntity<Course> updateCoursePartially(
            @PathVariable("courseName") String courseName,
            @RequestBody Course course
    ) throws NoSuchFieldException, IllegalAccessException {

        return ResponseEntity.ok(courseService.updateCoursePartially(courseName, course));
    }
}
