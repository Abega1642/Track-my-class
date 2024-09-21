package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.services.teacherServices.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
public class TeacherController {
    private TeacherService teacherService;

    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAllTeachers());
    }

    @GetMapping("/teacher/{teacherRef}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("teacherRef") String teacherRef) {
        Teacher teacher = teacherService.findTeacherById(teacherRef);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/teacher/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.addTeacher(teacher));
    }

    @PutMapping("/teacher/{teacherRef}/update")
    public ResponseEntity<Teacher> updateTeacherById(
            @PathVariable("teacherRef") String teacherRef,
            @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.updateTeacher(teacherRef, teacher));
    }

    @PatchMapping("/teacher/{teacherRef}/update")
    public ResponseEntity<Teacher> updateTeacherPartiallyById(
            @PathVariable("teacherRef") String teacherRef,
            @RequestBody Teacher teacher
    ) throws NoSuchFieldException, IllegalAccessException {
        return ResponseEntity.ok(teacherService.partialTeacherUpdate(teacherRef,teacher));
    }

}
