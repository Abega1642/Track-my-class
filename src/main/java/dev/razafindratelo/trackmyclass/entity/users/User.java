package dev.razafindratelo.trackmyclass.entity.users;

import lombok.Data;

@Data
public abstract class User {
    private String userRef;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    public User(String userRef, String lastName, String firstName, String email, String phoneNumber) {
        if(userRef == null) {
            this.userRef = "NoId";
        } else if(userRef.length() > 8) {
            throw new IllegalArgumentException("UserRef is too long");
        }
        this.userRef = userRef;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
