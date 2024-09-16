package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class DelayMatcher extends AbstrAttendanceMatcher {
    private List<Delay> delays;

    public DelayMatcher(Student student, List<Delay> delays) {
        super(student);
        this.delays = delays;
    }
}
