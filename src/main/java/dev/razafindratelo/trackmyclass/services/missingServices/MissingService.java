package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MissingService {
    List<MissingMatcher> findAllMissing();

    MissingMatcher findMissingByStudentRef(String studentRef);

    MissingMatcher findStudentMissingByCourseRef(String studentRef, String courseName);

    MissingMatcher findStudentMissingByCourseRefThisMonth(String studentRef, String courseName);

    MissingMatcher findNonJustifiedMissingByStudentRef(String studentRef);

    MissingMatcher findJustifiedMissingByStudentRef(String studentRef);

    MissingMatcher findMissingByStudentRefThisMonth(String studentRef);

    MissingMatcher findNonJustifiedMissingByStudentRefThisMonth(String studentRef);
}
