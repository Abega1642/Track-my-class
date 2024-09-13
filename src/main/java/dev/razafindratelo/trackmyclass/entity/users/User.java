package dev.razafindratelo.trackmyclass.entity.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class User {
    private String userRef;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
}
