package dev.razafindratelo.trackmyclass.services.attendanceServices;

import dev.razafindratelo.trackmyclass.dao.AttendanceDAO;
import dev.razafindratelo.trackmyclass.dto.*;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.GenericAttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import dev.razafindratelo.trackmyclass.services.delayServices.DelayService;
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
    private final DelayService delayService;


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
        Student student = studentService.findStudentById(studentRef.toUpperCase());
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
    public List<GenericAttendanceMatcher<?>> doAttendanceByLevelYear(GeneralAttendanceDTO missing) {
        String levelYear = courseService.getCourseLevelYear(missing.getCourseName()).toString().toUpperCase();

        // list of justified missing STDs
        List<String> justifiedMissingSTDs = studentService
                .filterExistingStudentByLevelYear(missing.getStdsWithMissingJustification(), levelYear);
        missing.setStdsWithMissingJustification(justifiedMissingSTDs);

        // list of unjustified missing STDs
        List<String> unjustifiedMissingSTDs = studentService
                .filterExistingStudentByLevelYear(missing.getStdsWithoutMissingJustification(), levelYear);

        missing.setStdsWithoutMissingJustification(unjustifiedMissingSTDs);

        // list of all missing STDS (whether justified or not)
        List<String> allMissingSTDs = new ArrayList<>();
        allMissingSTDs.addAll(justifiedMissingSTDs);
        allMissingSTDs.addAll(unjustifiedMissingSTDs);

        // list of all delay STDs with their respective lateness
        List<String> delayedSTDs = missing.getStudentDelays().stream().map(StudentDelayDTO::getStd).toList();

        List<String> filteredDelayedSTDs = studentService
                .filterExistingStudentByLevelYear(delayedSTDs, levelYear);

        List<StudentDelayDTO> delays = missing.getStudentDelays()
                .stream()
                .filter(d -> filteredDelayedSTDs.contains(d.getStd()))
                .toList();
        missing.setStudentDelays(delays);

        // list of all student STDs which were present at the course
        List<String> presentSTDs = studentService.filterPresentStdsByLevelYear(allMissingSTDs, levelYear);

        // list of all attendances (missing & presence & delays)  of all stds
        List<AttendanceMatcher> attendanceMatchers = addStudentsAttendances(presentSTDs, missing);
        List<MissingMatcher> missingMatchers = missingService
                .completeMissing(missing);
        List<DelayMatcher> delayMatchers = delayService.completeAddDelay(delays, missing);

        // result
        return completeAttendances(attendanceMatchers, missingMatchers, delayMatchers);
    }

    @Override
    public List<GenericAttendanceMatcher<?>> doAttendanceByGroup(GroupAttendanceDTO missing) {
        String group = missing.getGroup().toString().toUpperCase();

        // list of unjustified missing STDs
        List<String> unjustifiedMissingSTDs = studentService
                .filterExistingStudentByGroup(missing.getStdsWithoutMissingJustification(), group);

        missing.setStdsWithoutMissingJustification(unjustifiedMissingSTDs);

        // list of justified missing STDs
        List<String> justifiedMissingSTDs = studentService
                .filterExistingStudentByGroup(missing.getStdsWithMissingJustification(), group);

        missing.setStdsWithMissingJustification(justifiedMissingSTDs);

        // list of all missing STDS (whether justified or not)
        List<String> allMissingSTDs = new ArrayList<>();
        allMissingSTDs.addAll(justifiedMissingSTDs);
        allMissingSTDs.addAll(unjustifiedMissingSTDs);

        // list of all delay STDs with their respective lateness
        List<String> delayedSTDs = missing.getStudentDelays().stream().map(StudentDelayDTO::getStd).toList();

        List<String> filteredDelayedSTDs = studentService
                .filterExistingStudentByGroup(delayedSTDs, group);

        List<StudentDelayDTO> delays = missing.getStudentDelays()
                .stream()
                .filter(d -> filteredDelayedSTDs.contains(d.getStd()))
                .toList();
        missing.setStudentDelays(delays);

        // list of all student STDs which were present at the course
        List<String> presentSTDs = studentService.filterPresentStdsByGroup(allMissingSTDs, group);

        // list of all attendances (missing & presence & delays)  of all stds
        List<AttendanceMatcher> attendanceMatchers = addStudentsAttendances(presentSTDs, missing);
        List<MissingMatcher> missingMatchers = missingService
                .completeMissing(missing);

        List<DelayMatcher> delayMatchers = delayService.completeAddDelay(delays, missing);

        //result
        return completeAttendances(attendanceMatchers, missingMatchers, delayMatchers);
    }

    public List<GenericAttendanceMatcher<?>> completeAttendances(
            List<AttendanceMatcher> attendanceMatchers,
            List<MissingMatcher> missingMatchers,
            List<DelayMatcher> delayMatchers
            ) {

        List<GenericAttendanceMatcher<?>> result = new ArrayList<>();

        result.addAll(attendanceMatchers);
        result.addAll(missingMatchers);
        result.addAll(delayMatchers);

        return result;
    }

    public List<AttendanceMatcher> addStudentsAttendances(
            List<String> presentSTDs,
            GeneralAttendanceDTO missing
    ) {
        Teacher responsible = teacherService.findTeacherById(missing.getResponsibleRef().toUpperCase());
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
