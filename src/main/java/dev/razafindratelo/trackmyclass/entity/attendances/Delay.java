package dev.razafindratelo.trackmyclass.entity.attendances;

import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Delay extends Attendance {
    private LocalDateTime lateness;

    public Delay(
            LocalDateTime commencement,
            LocalDateTime termination,
            TeacherDTO attendanceResponsible,
            Course course,
            LocalDateTime lateness
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.lateness = lateness;
    }
}
