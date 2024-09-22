package dev.razafindratelo.trackmyclass.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public sealed class GeneralMissingDTO extends AbstractAttendanceDTO
    permits GroupMissingDTO
{
    private final List<String> stdsWithMissingJustification;
    private final List<String> stdsWithoutMissingJustification;
    private final String responsibleRef;

    public GeneralMissingDTO(
            String courseName,
            LocalDateTime commencement,
            LocalDateTime termination,
            List<String> stdsWithMissingJustification,
            List<String> stdsWithoutMissingJustification,
            String responsibleRef
    ) {
        super(courseName, commencement, termination);
        this.stdsWithMissingJustification = stdsWithMissingJustification;
        this.stdsWithoutMissingJustification = stdsWithoutMissingJustification;
        this.responsibleRef = responsibleRef;
    }
}
