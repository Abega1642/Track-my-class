package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.entity.matchers.LevelGroupMatcher;
import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Group;
import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Level;
import dev.razafindratelo.trackmyclass.services.groupAndLevelServices.GroupAndLevelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
