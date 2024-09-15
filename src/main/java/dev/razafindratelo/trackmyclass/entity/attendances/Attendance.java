package dev.razafindratelo.trackmyclass.entity.attendances;

import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Attendance {
    private LocalDateTime commencement;
    private LocalDateTime termination;
    private TeacherDTO attendanceResponsible;
    private Course course;
}
