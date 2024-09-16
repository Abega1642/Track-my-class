package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDAO attendanceDAO;
    private final StudentDAO studentDAO;

    @Override
    public List<AttendanceMatcher> findAllAttendances() {
        return attendanceDAO.getAllAttendance();
    }

    @Override
    public AttendanceMatcher findAttendancesByStudentRef(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return attendanceDAO.getAttendanceByStudent(student);
    }
}
