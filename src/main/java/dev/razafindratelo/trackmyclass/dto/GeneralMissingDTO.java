package dev.razafindratelo.trackmyclass.dto;

import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
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
    private String courseName;
    private Group group;
    private LocalDateTime commencement;
    private LocalDateTime termination;
    private List<String> stdsWithMissingJustification;
    private List<String> stdsWithoutMissingJustification;
}
