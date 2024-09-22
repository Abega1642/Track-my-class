package dev.razafindratelo.trackmyclass.services.delayServices;


import dev.razafindratelo.trackmyclass.dto.DelayDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralAttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.StudentDelayDTO;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface DelayService {
    List<DelayMatcher> findAllDelays();

    DelayMatcher findDelaysByStudentRef(String studentRef);

    List<DelayMatcher> completeAddDelay(
            List<StudentDelayDTO> lateStudentSTDs,
            GeneralAttendanceDTO generalMissingDTO
    );
    List<DelayMatcher> addDelay(DelayDTO delayDTO);
}
