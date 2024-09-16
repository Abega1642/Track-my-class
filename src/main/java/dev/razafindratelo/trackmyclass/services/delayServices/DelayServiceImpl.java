package dev.razafindratelo.trackmyclass.services.delayServices;

import dev.razafindratelo.trackmyclass.dao.DelayDAO;
import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
@AllArgsConstructor
public class DelayServiceImpl implements DelayService {
    private final DelayDAO delayDAO;
    private final StudentDAO studentDAO;

    @Override
    public List<DelayMatcher> findAllDelays() {
        List<String> stds = studentDAO.getAllStudentRef();
        List<DelayMatcher> delays = new ArrayList<>();

        for (String std : stds) {
            Student student = studentDAO.getStudentById(std);
            delays.add(delayDAO.getDelaysByStudent(student));
        }
        return delays;
    }

    @Override
    public DelayMatcher findDelaysByStudentRef(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return delayDAO.getDelaysByStudent(student);
    }
}
