package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.GroupMissingDTO;
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


    List<GenericAttendanceMatcher<?>> doAttendanceByLevelYear(String teacherRef, GeneralMissingDTO missing);

    List<GenericAttendanceMatcher<?>> doAttendanceByGroup(String teacherRef, GroupMissingDTO missing);

    List<AttendanceMatcher> addStudentsAttendances(
            List<String> presentSTDs,
            GeneralMissingDTO missing,
            String teacherRef
    );
}
