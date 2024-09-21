package dev.razafindratelo.trackmyclass.controllers;
import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
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

    @PostMapping("/teacher/{teacherRef}/attendance/add/{std}")
    public ResponseEntity<AttendanceMatcher> addStudentAttendance(
            @PathVariable("teacherRef") String teacherRef,
            @PathVariable("std") String std,
            @RequestBody Attendance attendance
    ) {
        return new ResponseEntity<>(
                attendanceService.addStudentAttendance(std, attendance),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/teacher/{teacherRef}/attendances/add")
    public ResponseEntity<List<GenericAttendanceMatcher<?>>> doAttendanceByLevelYear(
            @PathVariable("teacherRef") String teacherRef,
            @RequestBody GeneralMissingDTO generalMissingDTO,
            @RequestParam("type") String type
    ){

        return new ResponseEntity<>(
                attendanceService.handleAttendances(teacherRef, generalMissingDTO, type),
                HttpStatus.CREATED
        );

    }
}
