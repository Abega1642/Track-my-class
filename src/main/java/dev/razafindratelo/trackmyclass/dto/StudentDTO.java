package dev.razafindratelo.trackmyclass.dto;

import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentDTO extends UserDTO {
    private Level level;
    private Group group;

    public StudentDTO(
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
