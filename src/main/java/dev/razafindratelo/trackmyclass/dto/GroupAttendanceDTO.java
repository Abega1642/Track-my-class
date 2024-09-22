package dev.razafindratelo.trackmyclass.dto;

import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class GroupAttendanceDTO extends GeneralAttendanceDTO{
    private final Group group;

    public GroupAttendanceDTO(
            String courseName,
            Group group,
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
                studentDelays,
                responsibleRef
        );
        this.group = group;
    }
}
