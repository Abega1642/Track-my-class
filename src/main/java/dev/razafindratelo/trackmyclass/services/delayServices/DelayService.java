package dev.razafindratelo.trackmyclass.services.delayServices;


import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface DelayService {
    List<DelayMatcher> findAllDelays();

    List<DelayMatcher> findDelaysByStudentRef(String studentRef);
}
