package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.dto.AttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.GroupMissingDTO;
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
        List<String> stds = studentService.findAllStudentRef();
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
    public List<AttendanceMatcher> addStudentsAttendance(AttendanceDTO attendances) {
        List<AttendanceMatcher> attendanceMatchers = new ArrayList<>();
        Teacher responsible = teacherService.findTeacherById(attendances.getResponsibleRef());
        Course course = courseService.getCourseByName(attendances.getCourseName());

        Attendance theAttendance = new Attendance(
                attendances.getCommencement(),
                attendances.getTermination(),
                responsible,
                course
        );

        List<String> STDs = attendances.getSTDs();

        for(String std: STDs) {
            AttendanceMatcher attendance = addStudentAttendance(std, theAttendance);
            attendanceMatchers.add(attendance);
        }

        return attendanceMatchers;
    }

    @Override
    public List<GenericAttendanceMatcher<?>> doAttendanceByLevelYear(GeneralMissingDTO missing) {
        String levelYear = courseService.getCourseLevelYear(missing.getCourseName()).toString().toUpperCase();
        String teacherRef = missing.getResponsibleRef().toUpperCase();

        // list of justified missing STDs
        List<String> justifiedMissingSTDs = studentService
                .filterExistingStudentByLevelYear(missing.getStdsWithMissingJustification(), levelYear);

        // list of unjustified missing STDs
        List<String> unjustifiedMissingSTDs = studentService
                .filterExistingStudentByLevelYear(missing.getStdsWithoutMissingJustification(), levelYear);

        // list of all missing STDS (whether justified or not)
        List<String> allMissingSTDs = new ArrayList<>();
        allMissingSTDs.addAll(justifiedMissingSTDs);
        allMissingSTDs.addAll(unjustifiedMissingSTDs);

        // list of all student STDs which were present at the course
        List<String> presentSTDs = studentService.filterPresentStdsByLevelYear(allMissingSTDs, levelYear);

        // list of all attendances (missing & presence)  of all stds
        List<AttendanceMatcher> attendanceMatchers = addStudentsAttendances(presentSTDs, missing, teacherRef);
        List<MissingMatcher> missingMatchers = missingService
                .completeMissing(missing);

        // result
        return completeAttendances(attendanceMatchers, missingMatchers);
    }

    @Override
    public List<GenericAttendanceMatcher<?>> doAttendanceByGroup(GroupMissingDTO missing) {
        String group = missing.getGroup().toString().toUpperCase();
        String teacherRef = missing.getResponsibleRef().toUpperCase();

        // list of justified missing STDs
        List<String> justifiedMissingSTDs = studentService
                .filterExistingStudentByGroup(missing.getStdsWithMissingJustification(), group);

        // list of unjustified missing STDs
        List<String> unjustifiedMissingSTDs = studentService
                .filterExistingStudentByGroup(missing.getStdsWithoutMissingJustification(), group);

        // list of all missing STDS (whether justified or not)
        List<String> allMissingSTDs = new ArrayList<>();
        allMissingSTDs.addAll(justifiedMissingSTDs);
        allMissingSTDs.addAll(unjustifiedMissingSTDs);

        // list of all student STDs which were present at the course
        List<String> presentSTDs = studentService.filterPresentStdsByGroup(allMissingSTDs, group);

        // list of all attendances (missing & presence)  of all stds
        List<AttendanceMatcher> attendanceMatchers = addStudentsAttendances(presentSTDs, missing, teacherRef);
        List<MissingMatcher> missingMatchers = missingService
                .completeMissing(missing);

        return completeAttendances(attendanceMatchers, missingMatchers);
    }

    public List<GenericAttendanceMatcher<?>> completeAttendances(
            List<AttendanceMatcher> attendanceMatchers,
            List<MissingMatcher> missingMatchers) {

        List<GenericAttendanceMatcher<?>> result = new ArrayList<>();

        result.addAll(attendanceMatchers);
        result.addAll(missingMatchers);

        return result;
    }

    public List<AttendanceMatcher> addStudentsAttendances(
            List<String> presentSTDs,
            GeneralMissingDTO missing,
            String teacherRef
    ) {
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
