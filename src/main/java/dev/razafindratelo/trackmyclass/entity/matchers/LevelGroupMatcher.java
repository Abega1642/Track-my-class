package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Group;
import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LevelGroupMatcher {
    private Level level;
    private Group group;
}
