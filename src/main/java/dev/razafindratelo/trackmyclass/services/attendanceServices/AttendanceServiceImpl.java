package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.GenericAttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import dev.razafindratelo.trackmyclass.services.missingServices.MissingService;
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
    private final MissingService missingService;


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
    public List<GenericAttendanceMatcher<?>> doAttendance(String teacherRef, MissingDTO missing) {

        // list of justified missing STDs
        List<String> justifiedMissingSTDs = studentService
                .filterExistingStudents(missing.getStdsWithMissingJustification());

        // list of unjustified missing STDs
        List<String> unjustifiedMissingSTDs = studentService
                .filterExistingStudents(missing.getStdsWithoutMissingJustification());

        // list of presents STDS
        List<String> allMissingSTDs = new ArrayList<>();
        allMissingSTDs.addAll(justifiedMissingSTDs);
        allMissingSTDs.addAll(unjustifiedMissingSTDs);
        List<String> presentSTDs = studentService.filterPresentStds(allMissingSTDs);

        // list of all attendances (missing & presence)  of all stds
        List<AttendanceMatcher> attendances = doPresence(presentSTDs, missing, teacherRef);
        List<MissingMatcher> missingMatchers = new ArrayList<>();
        missingMatchers.addAll(missingService.addJustifiedMissing(justifiedMissingSTDs, missing, teacherRef));
        missingMatchers.addAll(missingService.addUnjustifiedMissing(unjustifiedMissingSTDs, missing, teacherRef));
        System.out.println(missingMatchers);
        for (MissingMatcher missingMatcher : missingMatchers) {
            System.out.println(missingMatcher);
        }
        // result
        List<GenericAttendanceMatcher<?>> result = new ArrayList<>();
        result.addAll(attendances);
        result.addAll(missingMatchers);

        return result;

    }

    public List<AttendanceMatcher> doPresence(List<String> presentSTDs, MissingDTO missing, String teacherRef) {

        Teacher responsible = teacherService.findTeacherById(teacherRef);
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
