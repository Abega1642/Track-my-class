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
public class CorDTO {
    private String corRef;
    private String std;
    private LocalDateTime corDate;
}
