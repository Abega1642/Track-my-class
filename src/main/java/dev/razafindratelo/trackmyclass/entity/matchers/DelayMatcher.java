package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.dto.StudentDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayMatcher{
    private StudentDTO student;
    private Delay delay;

}
