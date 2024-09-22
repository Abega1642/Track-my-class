package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dto.AttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralAttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.GroupAttendanceDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.GenericAttendanceMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface AttendanceService {
    List<AttendanceMatcher> findAllAttendances();

    AttendanceMatcher findAttendancesByStudentRef(String studentRef);

    AttendanceMatcher addStudentAttendance(String std, Attendance attendance);

    List<AttendanceMatcher> addStudentsAttendance(AttendanceDTO attendances);

    List<GenericAttendanceMatcher<?>> doAttendanceByLevelYear(GeneralAttendanceDTO missing);

    List<GenericAttendanceMatcher<?>> doAttendanceByGroup(GroupAttendanceDTO missing);

    List<AttendanceMatcher> addStudentsAttendances(
            List<String> presentSTDs,
            GeneralAttendanceDTO missing
    );
}
