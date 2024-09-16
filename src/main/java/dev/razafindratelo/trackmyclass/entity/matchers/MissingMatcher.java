package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MissingMatcher extends AbstrAttendanceMatcher {
    private List<Missing> missingList;

    public MissingMatcher(Student student, List<Missing> missingList) {
        super(student);
        this.missingList = missingList;
    }
}
