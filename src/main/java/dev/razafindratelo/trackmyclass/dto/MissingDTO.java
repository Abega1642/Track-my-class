package dev.razafindratelo.trackmyclass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class MissingDTO {
    private String responsibleRef;
    private String courseName;
    private LocalDateTime commencement;
    private LocalDateTime termination;
    private List<String> stdList;
}
