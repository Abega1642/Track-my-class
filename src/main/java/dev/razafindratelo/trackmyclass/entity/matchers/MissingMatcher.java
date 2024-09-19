package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class MissingMatcher extends GenericAttendanceMatcher<Missing> {

    public MissingMatcher(Student student, List<Missing> missingList) {
        super(student, missingList);
    }
}
