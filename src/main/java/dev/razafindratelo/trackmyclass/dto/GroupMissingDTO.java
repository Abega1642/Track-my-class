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
public final class GroupMissingDTO extends GeneralMissingDTO{
    private final Group group;

    public GroupMissingDTO(
            String courseName,
            Group group,
            LocalDateTime commencement,
            LocalDateTime termination,
            List<String> stdsWithMissingJustification,
            List<String> stdsWithoutMissingJustification,
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
        this.group = group;
    }
}
