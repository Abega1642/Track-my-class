package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MissingService {
    List<MissingMatcher> findAllMissing();

    MissingMatcher findStudentMissingByCourse(String studentRef, String courseName, Integer month, Integer year, String condition);

    MissingMatcher findMissingByStudent(String studentRef, String condition);
}
