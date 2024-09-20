package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController("/student")
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @GetMapping("/{std}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable("std") String std) {
        return ResponseEntity.ok(studentService.findStudentById(std));
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = studentService.insertStudent(student);
        return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
    }
}
