package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DelayMatcher extends GenericAttendanceMatcher<Delay> {

    public DelayMatcher(Student student, List<Delay> delays) {
        super(student, delays);
    }
}
