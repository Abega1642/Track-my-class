package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MissingService {
    List<MissingMatcher> findAllMissing();

    MissingMatcher findStudentMissingByCourse(
            String studentRef,
            String courseName,
            Integer month,
            Integer year,
            String condition
    );

    MissingMatcher findAllStudentMissingByCourse(String studentRef, String courseName, String condition);

    MissingMatcher findMissingByStudent(String studentRef, String condition);

    List<MissingMatcher> addMissing(
            List<String> missingSTDs,
            GeneralMissingDTO generalMissingDTO,
            boolean isJustified
    );

    List<MissingMatcher> completeMissing(GeneralMissingDTO generalMissingDTO);
}
