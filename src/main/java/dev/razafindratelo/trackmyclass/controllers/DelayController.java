package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.services.delayServices.DelayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
public class DelayController {
    private final DelayService delayService;

    @GetMapping("/student/delays")
    public ResponseEntity<List<DelayMatcher>> getAllDelays() {
        return ResponseEntity.ok(delayService.findAllDelays());
    }

    @GetMapping("/student/{std}/delays")
    public ResponseEntity<DelayMatcher> getStudentDelays(@PathVariable("std") String std) {
        return ResponseEntity.ok(delayService.findDelaysByStudentRef(std));
    }
}
