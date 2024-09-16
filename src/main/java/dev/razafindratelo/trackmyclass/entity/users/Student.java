package dev.razafindratelo.trackmyclass.entity.users;

import dev.razafindratelo.trackmyclass.entity.attendances.Absence;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Student extends User {
    private Level level;
    private Group group;

    public Student(
            String userRef,
            String lastName,
            String firstName,
            String email,
            String phoneNumber,
            Level level,
            Group group
    ) {
        super(userRef, lastName, firstName, email, phoneNumber);

        if(!doesLevelMatchesToGroup(level, group)) {
            throw new IllegalArgumentException("Level doesn't match to group");
        } else {
            this.level = level;
            this.group = group;
        }
    }

    private static boolean doesLevelMatchesToGroup(Level level, Group group) {
        return levelsGroup(level).contains(group);
    }

    private static List<Group> levelsGroup(Level level) {
        List<Group> groups = new ArrayList<>();
        for(Group group : Group.values()) {
            if(group.getLevel().equals(level)) {
                groups.add(group);
            }
        }
        return groups;
    }
}
