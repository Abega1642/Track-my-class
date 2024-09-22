package dev.razafindratelo.trackmyclass.controllers;
import dev.razafindratelo.trackmyclass.dto.AttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralAttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.GroupAttendanceDTO;
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
    public ResponseEntity<List<AttendanceMatcher>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping("/student/{std}/attendances")
    public ResponseEntity<AttendanceMatcher> getAttendancesByStudentId(
            @PathVariable("std") String std) {
        return ResponseEntity.ok(attendanceService.findAttendancesByStudentRef(std));
    }

    @PostMapping("/attendances/add/student")
    public ResponseEntity<List<AttendanceMatcher>> addStudentsAttendance(@RequestBody AttendanceDTO attendance) {
        return new ResponseEntity<>(
                attendanceService.addStudentsAttendance(attendance),
                HttpStatus.CREATED
        );
    }

    @PostMapping("attendances/add/level")
    public ResponseEntity<List<GenericAttendanceMatcher<?>>> doAttendanceByLevelYear(
            @RequestBody GeneralAttendanceDTO generalMissingDTO
    ){
        return new ResponseEntity<>(
                attendanceService.doAttendanceByLevelYear(generalMissingDTO),
                HttpStatus.CREATED
        );
    }
    @PostMapping("attendances/add/group")
    public ResponseEntity<List<GenericAttendanceMatcher<?>>> doAttendanceByGroup(
            @RequestBody GroupAttendanceDTO groupAttendanceDTO
    ){
        return new ResponseEntity<>(
                attendanceService.doAttendanceByGroup(groupAttendanceDTO),
                HttpStatus.CREATED
        );
    }
}
