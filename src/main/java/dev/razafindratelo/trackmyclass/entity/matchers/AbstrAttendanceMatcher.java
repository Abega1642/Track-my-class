package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public abstract class AbstrAttendanceMatcher {
    private Student student;
}
