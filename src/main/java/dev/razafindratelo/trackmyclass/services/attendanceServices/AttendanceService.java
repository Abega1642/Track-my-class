package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    List<AttendanceMatcher> findAllAttendances();

    AttendanceMatcher findAttendancesByStudentRef(String studentRef);

    AttendanceMatcher addStudentAttendance(String std, Attendance attendance);

    List<AttendanceMatcher> doAttendance(MissingDTO missing);
}
