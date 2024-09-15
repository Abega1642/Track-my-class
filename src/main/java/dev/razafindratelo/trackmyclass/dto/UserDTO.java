package dev.razafindratelo.trackmyclass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public abstract class UserDTO {
    private String userRef;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    
}
