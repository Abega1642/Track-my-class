package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class AttendanceMatcher extends AbstrAttendanceMatcher{
    private List<Attendance> attendance;

    public AttendanceMatcher(Student student, List<Attendance> attendance) {
        super(student);
        this.attendance = attendance;
    }
}
