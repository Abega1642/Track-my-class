package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDAO attendanceDAO;

    public AttendanceServiceImpl(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    @Override
    public List<AttendanceMatcher> findAllAttendances() {
        return attendanceDAO.getAllAttendance();
    }

    @Override
    public List<AttendanceMatcher> findAttendancesByStudentRef(String studentRef) {
        return attendanceDAO.getAttendanceByStudentId(studentRef);
    }
}
