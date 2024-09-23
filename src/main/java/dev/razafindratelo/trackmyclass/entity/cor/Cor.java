package dev.razafindratelo.trackmyclass.entity.cor;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Cor implements Serializable {
    private String corRef;
    private Student student;
    private LocalDateTime corDate;
}
