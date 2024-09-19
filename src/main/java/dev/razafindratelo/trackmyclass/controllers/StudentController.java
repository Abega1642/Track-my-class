package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @GetMapping("/student/{std}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable("std") String std) {
        return ResponseEntity.ok(studentService.findStudentById(std));
    }
}
