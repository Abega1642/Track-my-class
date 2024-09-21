package dev.razafindratelo.trackmyclass.controllers;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.GenericAttendanceMatcher;
import dev.razafindratelo.trackmyclass.services.attendanceServices.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/student/attendances")
    public ResponseEntity<List<AttendanceMatcher>> test() {
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping("/student/{std}/attendances")
    public ResponseEntity<AttendanceMatcher> getAttendancesById(
            @PathVariable("std") String std) {
        return ResponseEntity.ok(attendanceService.findAttendancesByStudentRef(std));
    }

    @PostMapping("/teacher/{teacherRef}/attendances")
    public ResponseEntity<List<GenericAttendanceMatcher<?>>> doAttendance(
            @PathVariable("teacherRef") String teacherRef,
            @RequestBody MissingDTO missingDTO ){
        return new ResponseEntity<>(attendanceService.doAttendance(teacherRef, missingDTO), HttpStatus.CREATED);
    }
}
