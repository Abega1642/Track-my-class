package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    List<AttendanceMatcher> findAllAttendances();

    AttendanceMatcher findAttendancesByStudentRef(String studentRef);
}
