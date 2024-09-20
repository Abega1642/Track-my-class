package dev.razafindratelo.trackmyclass.entity.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Course {
    private String courseRef;
    private String name;
    private int credit;

}
