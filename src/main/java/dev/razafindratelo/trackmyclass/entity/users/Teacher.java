package dev.razafindratelo.trackmyclass.entity.users;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
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
