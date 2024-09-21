package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class GroupAndLevelDAO {
    private DBConnection dbConnection;

    public Level updateLevelToNextLevel(Level level) {
        int number = Integer.parseInt(level.toString().substring(1,2));
        String letter = level.toString().substring(0,1);
        if(letter.equals("L") && number == 3) {
            level = Level.M1;
        } else if (letter.equals("M") && number == 2) {
            level = Level.OUT;
        } else {
            number++;
            level = Level.valueOf(letter + number);
        }
        return level;
    }

    public Group updateLeveLAndGroupToDate(Group group) {
        group.setLevel(updateLevelToNextLevel(group.getLevel()));
        return group;
    }

}
