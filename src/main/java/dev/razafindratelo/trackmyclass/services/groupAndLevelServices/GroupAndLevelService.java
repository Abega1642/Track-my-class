package dev.razafindratelo.trackmyclass.services.groupAndLevelServices;

import dev.razafindratelo.trackmyclass.entity.matchers.LevelGroupMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface GroupAndLevelService {
    List<LevelGroupMatcher> getAllLevelAndGroupRelations();

    List<Group> getAllGroups();

    List<Level> getAllLevels();

    boolean checkLevelAndGroupMap(Group group, Level level);

    List<LevelGroupMatcher> updateGroupAndLevelRelation();

    List<Student> updateAllLevelGroupRelationAndStudentToNextLevelYear(List<String> excludeSTDs);

}
