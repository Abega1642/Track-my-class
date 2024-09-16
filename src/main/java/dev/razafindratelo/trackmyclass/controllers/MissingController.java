package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.services.missingServices.MissingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MissingController {
    private final MissingService missingService;

    @GetMapping("/student/{std}/missing/course")
    public MissingMatcher studentMissing(
            @PathVariable("std") String std,
            @RequestParam("course_name") String courseName) {
        return missingService.findStudentMissingByCourseRef(std, courseName);
    }

    @GetMapping("/student/{std}/missing")
    public MissingMatcher getJustifiedOrNoTMissingByStudent(
            @PathVariable("std") String std,
            @RequestParam("is_justified") String isJustified
            ) {
        if (isJustified.equalsIgnoreCase("no")) {
            return missingService.findNonJustifiedMissingByStudentRef(std);
        } else if (isJustified.equalsIgnoreCase("yes")) {
            return missingService.findJustifiedMissingByStudentRef(std);
        } else {
            return missingService.findMissingByStudentRef(std);
        }
    }
}
