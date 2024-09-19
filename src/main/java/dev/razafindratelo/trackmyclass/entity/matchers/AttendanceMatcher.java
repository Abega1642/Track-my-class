package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AttendanceMatcher extends GenericAttendanceMatcher<Attendance>{

    public AttendanceMatcher(Student student, List<Attendance> attendances) {
        super(student, attendances);
    }
}
