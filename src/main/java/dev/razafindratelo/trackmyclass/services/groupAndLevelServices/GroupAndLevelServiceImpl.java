package dev.razafindratelo.trackmyclass.services.groupAndLevelServices;

import dev.razafindratelo.trackmyclass.dao.GroupAndLevelDAO;
import dev.razafindratelo.trackmyclass.entity.matchers.LevelGroupMatcher;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@AllArgsConstructor
public class GroupAndLevelServiceImpl implements GroupAndLevelService {
    private final GroupAndLevelDAO groupAndLevelDAO;

    @Override
    public List<LevelGroupMatcher> getAllLevelAndGroupRelations() {
        return groupAndLevelDAO.getAllRelationOfLeveAndGroup();
    }

    @Override
    public List<Group> getAllGroups() {
        return groupAndLevelDAO.allGroups();
    }

    @Override
    public List<Level> getAllLevels() {
        return groupAndLevelDAO.allLevels();
    }

    @Override
    public boolean checkLevelAndGroupMap(Group group, Level level) {
        return groupAndLevelDAO.doesLevelMatchGroup(group, level);
    }

    @Override
    public List<LevelGroupMatcher> updateGroupAndLevelRelations() {
        return groupAndLevelDAO.updateGroupLevels();
    }

}
