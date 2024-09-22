package dev.razafindratelo.trackmyclass.dto;

import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AttendanceDTO {
    private List<String> STDs;
    private Attendance attendance;
}
