package dev.razafindratelo.trackmyclass.services.groupAndLevelServices;

import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import org.springframework.stereotype.Service;

@Service
public interface GroupAndLevelService {
    boolean checkLevelAndGroupMap(Group group, Level level);
}
