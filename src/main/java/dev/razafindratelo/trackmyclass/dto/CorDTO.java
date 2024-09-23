package dev.razafindratelo.trackmyclass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CorDTO {
    private String corRef;
    private String std;
}
