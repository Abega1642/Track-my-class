package dev.razafindratelo.trackmyclass.entity.users;

import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Group;
import dev.razafindratelo.trackmyclass.entity.users.groupsAndLevels.Level;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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
        this.level = level;
        this.group = group;
    }
}
