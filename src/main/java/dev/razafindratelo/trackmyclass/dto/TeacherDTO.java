package dev.razafindratelo.trackmyclass.dto;


import lombok.Getter;

@Getter
public class TeacherDTO extends UserDTO {
    private final boolean isAssistant;

    public TeacherDTO(
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
