package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.services.missingServices.MissingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class MissingController {
    private final MissingService missingService;

    @GetMapping("/student/missing")
    public ResponseEntity<List<MissingMatcher>> getAllMissing() {
        return ResponseEntity.ok(missingService.findAllMissing());
    }

    @GetMapping("/student/{std}/missing/course")
    public ResponseEntity<MissingMatcher> getStudentMissingByCourse(
            @PathVariable("std") String std,
            @RequestParam("course_name") String courseName,
            @RequestParam(value = "is_justified", required = false) String condition,
            @RequestParam(value = "month_value", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year
    ) {
        return ResponseEntity.ok(missingService.findStudentMissingByCourse(std, courseName,month, year, condition));
    }

    @GetMapping("/student/{std}/missing")
    public ResponseEntity<MissingMatcher> getJustifiedOrNoTMissingByStudent(
            @PathVariable("std") String std,
            @RequestParam(value = "is_justified", required = false) String condition
    ) {
        return ResponseEntity.ok(missingService.findMissingByStudent(std, condition));
    }

    @PostMapping("/missing/add/student")
    public ResponseEntity<List<MissingMatcher>> addStudentsMissing(
            @RequestBody GeneralMissingDTO missing
    ) {
        return new ResponseEntity<>(
                missingService.completeMissing(missing),
                HttpStatus.CREATED
        );
    }

    @PutMapping("student/{std}/missing/update")
    public ResponseEntity<MissingMatcher> updateStudentMissingByStudent(
            @PathVariable("std") String std,
            @RequestParam("course_name") String courseName,
            @RequestParam("course_started_at") LocalDateTime courseStartedAt,
            @RequestParam("course_ended_at") LocalDateTime courseEndedAt,
            @RequestBody MissingDTO missing
    ) {
        return new ResponseEntity<>(
                missingService.updateMissing(
                    courseName,
                    courseStartedAt,
                    courseEndedAt,
                    missing
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/student/{std}/missing/delete")
    public ResponseEntity<Missing> deleteMissing(
            @PathVariable("std") String std,
            @RequestParam("course_name") String courseName,
            @RequestParam("course_started_at") LocalDateTime courseStartedAt,
            @RequestParam("course_ended_at") LocalDateTime courseEndedAt,
            @RequestParam("responsibleRef") String responsibleRef
    ) {
        return ResponseEntity.ok(missingService.deleteMissing(
                courseName,
                std,
                courseStartedAt,
                courseEndedAt,
                responsibleRef
        ));
    }
}
