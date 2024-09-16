package dev.razafindratelo.trackmyclass.services.delayServices;

import dev.razafindratelo.trackmyclass.dao.DelayDAO;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;

@Data
@Service
@AllArgsConstructor
public class DelayServiceImpl implements DelayService {
    private final DelayDAO delayDAO;

    @Override
    public List<DelayMatcher> findAllDelays() {
        return delayDAO.getAllDelays();
    }

    @Override
    public List<DelayMatcher> findDelaysByStudentRef(String studentRef) {
        return List.of();
    }
}
