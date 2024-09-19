package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public sealed abstract class GenericAttendanceMatcher<T>
    permits DelayMatcher,
    MissingMatcher,
    AttendanceMatcher {

    private Student student;
    private List<T> attendances;
}
