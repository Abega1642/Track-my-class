package dev.razafindratelo.trackmyclass.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AttendanceDTO extends AbstractAttendanceDTO {
    private final List<String> STDs;
    private final String responsibleRef;

    public AttendanceDTO(
            String courseName,
            LocalDateTime commencement,
            LocalDateTime termination,
            List<String> STDs,
            String responsibleRef
    ) {
        super(courseName, commencement, termination);
        this.STDs = STDs;
        this.responsibleRef = responsibleRef;
    }
}
