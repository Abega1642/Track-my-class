package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    MissingMatcher updateMissing(
            String courseName,
            LocalDateTime commencement,
            LocalDateTime termination,
            MissingDTO missingDTO
    );

    Missing deleteMissing(
            String courseName,
            String studentRef,
            LocalDateTime commencement,
            LocalDateTime termination,
            String responsibleRef
    );
}
