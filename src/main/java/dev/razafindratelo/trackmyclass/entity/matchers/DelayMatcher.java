package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class DelayMatcher extends AbstrAttendanceMatcher {
    private List<Delay> delay;

    public DelayMatcher(Student student, List<Delay> delay) {
        super(student);
        this.delay = delay;
    }
}
