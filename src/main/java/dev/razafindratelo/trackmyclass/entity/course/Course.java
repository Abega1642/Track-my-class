package dev.razafindratelo.trackmyclass.entity.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
    private String courseRef;
    private String name;
    private int credit;

    public Course(String courseRef, String name, int credit) {
        this.courseRef = courseRef;
        this.name = name;
        this.credit = credit;
    }
}
