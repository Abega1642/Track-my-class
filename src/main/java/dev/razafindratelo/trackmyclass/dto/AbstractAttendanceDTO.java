package dev.razafindratelo.trackmyclass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public sealed abstract class AbstractAttendanceDTO
    permits
        GeneralMissingDTO,
        AttendanceDTO
{
    private String courseName;
    private LocalDateTime commencement;
    private LocalDateTime termination;
}
