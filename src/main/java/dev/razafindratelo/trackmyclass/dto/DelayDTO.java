package dev.razafindratelo.trackmyclass.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DelayDTO extends AbstractAttendanceDTO{
    private String responsibleRef;
    private List<StudentDelayDTO> delays;
    public DelayDTO(
            String courseName,
            LocalDateTime commencement,
            LocalDateTime termination,
            String responsibleRef,
            List<StudentDelayDTO> delays
    ) {
        super(courseName, commencement, termination);
        this.responsibleRef = responsibleRef;
        this.delays = delays;
    }
}
