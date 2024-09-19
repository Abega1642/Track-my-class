package dev.razafindratelo.trackmyclass.entity.users;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Teacher extends User {
    private boolean isAssistant;

    public Teacher(
            String userRef,
            String lastName,
            String firstName,
            String email,
            String phoneNumber,
            boolean isAssistant
    ) {
        super(userRef, lastName, firstName, email, phoneNumber);
        this.isAssistant = isAssistant;
    }
}
