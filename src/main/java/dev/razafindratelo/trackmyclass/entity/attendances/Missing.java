package dev.razafindratelo.trackmyclass.entity.attendances;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Missing extends Attendance {
    private boolean isJustified;

    public Missing(
            LocalDateTime commencement,
            LocalDateTime termination,
            Teacher attendanceResponsible,
            Course course,
            boolean isJustified
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.isJustified = isJustified;
    }

    public Missing(
            LocalDateTime commencement,
            LocalDateTime termination,
            Teacher attendanceResponsible,
            Course course
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.isJustified = false;
    }

}
