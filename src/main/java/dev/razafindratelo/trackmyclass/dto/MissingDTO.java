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
public class MissingDTO {
    private String studentRef;
    private String responsibleRef;
    private LocalDateTime commencement;
    private LocalDateTime termination;
    private boolean isJustified;
}
