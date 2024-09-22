package dev.razafindratelo.trackmyclass.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public sealed class GeneralAttendanceDTO extends GeneralMissingDTO permits GroupAttendanceDTO {
    private List<StudentDelayDTO> studentDelays;

    public GeneralAttendanceDTO(
            String courseName,
            LocalDateTime commencement,
            LocalDateTime termination,
            List<String> stdsWithMissingJustification,
            List<String> stdsWithoutMissingJustification,
            List<StudentDelayDTO> studentDelays,
            String responsibleRef
    ) {
        super(
                courseName,
                commencement,
                termination,
                stdsWithMissingJustification,
                stdsWithoutMissingJustification,
                responsibleRef
        );
        this.studentDelays = studentDelays;
    }
}
