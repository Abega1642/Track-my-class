package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @GetMapping("/student/{std}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable("std") String std) {
        return ResponseEntity.ok(studentService.findStudentById(std));
    }

    @PostMapping("/student/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = studentService.insertStudent(student);
        return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/student/upgrade")
    public ResponseEntity<List<Student>> upgradeStudentsLevels(@RequestBody List<String> excludedSTDs) {
        return ResponseEntity.ok(studentService.completeUpgradeStudentsLevels(excludedSTDs));
    }

    @PutMapping("/student/{std}/update")
    public ResponseEntity<Student> updateStudentIntegrally(
            @PathVariable("std") String std,
            @RequestBody Student student
    ) {
        return ResponseEntity.ok(studentService.updateStudentIntegrally(std, student));
    }

    @PatchMapping("/student/{std}/update")
    public ResponseEntity<Student> updateStudentPartially(
            @PathVariable("std") String std,
            @RequestBody Student student
    ) throws NoSuchFieldException, IllegalAccessException {
        return ResponseEntity.ok(studentService.updateStudentPartially(std, student));
    }

    @DeleteMapping("/student/delete/{std}")
    public ResponseEntity<Student> deleteStudentBy(@PathVariable("std") String std) {
        return ResponseEntity.ok(studentService.deleteStudent(std));
    }
}
