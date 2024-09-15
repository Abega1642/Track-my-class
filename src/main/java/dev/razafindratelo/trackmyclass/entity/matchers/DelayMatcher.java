package dev.razafindratelo.trackmyclass.entity.matchers;

import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayMatcher{
    private Student student;
    private Delay delay;

}
