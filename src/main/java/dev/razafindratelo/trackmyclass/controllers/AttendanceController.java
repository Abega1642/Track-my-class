package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttendanceController {
    @GetMapping("/student/attendances")
    public ResponseEntity<List<AttendanceMatcher>> test() {
        var db = new DBConnection();
        var att = new AttendanceDAO(db);
        return ResponseEntity.ok(att.getAllAttendance());
    }

    @GetMapping("/student/{std}/attendances")
    public ResponseEntity<List<AttendanceMatcher>> getAttendancesById(
            @PathVariable("std") String std) {
        var db = new DBConnection();
        var att = new AttendanceDAO(db);
        return ResponseEntity.ok(att.getAttendanceByStudentId(std));
    }
}
