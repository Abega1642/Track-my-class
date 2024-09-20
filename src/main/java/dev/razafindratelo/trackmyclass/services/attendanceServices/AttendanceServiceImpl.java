package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import dev.razafindratelo.trackmyclass.services.missingServices.MissingServiceImpl;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import dev.razafindratelo.trackmyclass.services.teacherServices.TeacherService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDAO attendanceDAO;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final MissingServiceImpl missingServiceImpl;


    @Override
    public List<AttendanceMatcher> findAllAttendances() {
        List<String> stds = studentService.getAllStudentsRef();
        List<AttendanceMatcher> attendances = new ArrayList<>();

        for (String std : stds) {
            Student student = studentService.findStudentById(std);
            attendances.add(attendanceDAO.getAttendanceByStudent(student));
        }
        return attendances;
    }

    @Override
    public AttendanceMatcher findAttendancesByStudentRef(String studentRef) {
        Student student = studentService.findStudentById(studentRef);
        return attendanceDAO.getAttendanceByStudent(student);
    }

    @Override
    public AttendanceMatcher addStudentAttendance(String std, Attendance attendance) {
        Student student = studentService.findStudentById(std);
        Attendance attendances = attendanceDAO.addStudentAttendance(
                std,
                attendance.getCourse(),
                attendance.getAttendanceResponsible(),
                attendance.getCommencement(),
                attendance.getTermination()
        );

        return new AttendanceMatcher(
                student,
                List.of(attendances)
        );
    }

    @Override
    public List<AttendanceMatcher> doAttendance(MissingDTO missing) {
        List<String> presentSTDs = missing.getStdList();
        List<String> allSTDs = studentService.getAllStudentsRef();
        presentSTDs =  presentSTDs
                .stream()
                .filter(studentService::checkIfStudentExists)
                .toList();
        Teacher responsible = teacherService.findTeacherById(missing.getResponsibleRef());
        Course course = courseService.getCourseByName(missing.getCourseName());
        List<AttendanceMatcher> attendances = new ArrayList<>();

        for(String std : presentSTDs) {
            Student student = studentService.findStudentById(std);
            Attendance attendance = attendanceDAO.addStudentAttendance(
                    std,
                    course,
                    responsible,
                    missing.getCommencement(),
                    missing.getTermination()
            );
            attendances.add(new AttendanceMatcher(student, List.of(attendance)));
        }
        return attendances;
    }
}
