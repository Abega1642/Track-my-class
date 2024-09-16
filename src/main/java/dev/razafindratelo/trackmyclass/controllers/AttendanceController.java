package dev.razafindratelo.trackmyclass.controllers;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.services.attendanceServices.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<List<AttendanceMatcher>> getAttendancesById(
            @PathVariable("std") String std) {
        return ResponseEntity.ok(attendanceService.findAttendancesByStudentRef(std));
    }
}
