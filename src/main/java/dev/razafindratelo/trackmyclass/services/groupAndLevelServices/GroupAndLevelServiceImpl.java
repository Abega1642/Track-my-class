package dev.razafindratelo.trackmyclass.services.groupAndLevelServices;

import dev.razafindratelo.trackmyclass.dao.GroupAndLevelDAO;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class GroupAndLevelServiceImpl implements GroupAndLevelService {
    private final GroupAndLevelDAO groupAndLevelDAO;

    @Override
    public boolean checkLevelAndGroupMap(Group group, Level level) {
        return groupAndLevelDAO.doesLevelMatchGroup(group, level);
    }


}
