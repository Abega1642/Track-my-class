package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.services.missingServices.MissingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
