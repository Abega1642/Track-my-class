package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.*;

@Getter
@Setter
public class DelayMatcher extends AbstrAttendanceMatcher {
    private Delay delay;

    public DelayMatcher(Student student, Delay delay) {
        super(student);
        this.delay = delay;
    }
}
