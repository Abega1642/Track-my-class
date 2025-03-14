package dev.razafindratelo.trackmyclass.entity.attendances;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Attendance {
    private LocalDateTime commencement;
    private LocalDateTime termination;
    private Teacher attendanceResponsible;
    private Course course;
}
