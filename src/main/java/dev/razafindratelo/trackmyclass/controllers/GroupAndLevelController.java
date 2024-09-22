package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.matchers.LevelGroupMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import dev.razafindratelo.trackmyclass.services.groupAndLevelServices.GroupAndLevelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
public class GroupAndLevelController {
    private final GroupAndLevelService groupAndLevelService;

    @GetMapping("/groups&Levels")
    public ResponseEntity<List<LevelGroupMatcher>> getGroupAndLevelMapping() {
        return ResponseEntity.ok(groupAndLevelService.getAllLevelAndGroupRelations());
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getGroups() {
        return ResponseEntity.ok(groupAndLevelService.getAllGroups());
    }

    @GetMapping("/levels")
    public ResponseEntity<List<Level>> getLevels() {
        return ResponseEntity.ok(groupAndLevelService.getAllLevels());
    }

    @PutMapping("/student/upgrade")
    public ResponseEntity<List<Student>> upgrade(List<String> excludedSTDs) {
        return new ResponseEntity<>(
                groupAndLevelService.updateAllLevelGroupRelationAndStudentToNextLevelYear(excludedSTDs),
                HttpStatus.OK
        );
    }
}
