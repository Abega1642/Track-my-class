package dev.razafindratelo.trackmyclass.entity.attendances;

import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Missing extends Attendance {
    private boolean isJustified;

    public Missing(
            LocalDateTime commencement,
            LocalDateTime termination,
            TeacherDTO attendanceResponsible,
            Course course,
            boolean isJustified
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.isJustified = isJustified;
    }

    public Missing(
            LocalDateTime commencement,
            LocalDateTime termination,
            TeacherDTO attendanceResponsible,
            Course course
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.isJustified = false;
    }

}
