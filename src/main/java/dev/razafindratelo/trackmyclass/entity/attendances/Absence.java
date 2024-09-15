package dev.razafindratelo.trackmyclass.entity.attendances;

import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Absence extends Attendance {
    private boolean isJustified;

    public Absence(
            LocalDateTime commencement,
            LocalDateTime termination,
            TeacherDTO attendanceResponsible,
            Course course,
            boolean isJustified
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.isJustified = isJustified;
    }

    public Absence(
            LocalDateTime commencement,
            LocalDateTime termination,
            TeacherDTO attendanceResponsible,
            Course course
    ) {
        super(commencement, termination, attendanceResponsible, course);
        this.isJustified = false;
    }

}
