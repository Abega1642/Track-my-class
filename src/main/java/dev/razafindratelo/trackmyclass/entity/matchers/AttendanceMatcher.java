package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.dto.StudentDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceMatcher {
    private StudentDTO student;
    private Attendance attendance;
}
