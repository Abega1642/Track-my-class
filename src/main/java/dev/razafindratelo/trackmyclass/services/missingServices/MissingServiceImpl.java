package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.dao.MissingDAO;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import dev.razafindratelo.trackmyclass.services.teacherServices.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MissingServiceImpl implements MissingService {
    private final MissingDAO missingDAO;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Override
    public List<MissingMatcher> findAllMissing() {
        List<String> studentRef = studentService.getAllStudentsRef();
        List<MissingMatcher> missingList = new ArrayList<>();

        for (String std : studentRef) {
            Student student = studentService.findStudentById(std);
            missingList.add(missingDAO.getMissingByStudent(student));
        }
        return missingList;
    }

    @Override
    public MissingMatcher findMissingByStudent(String studentRef, String condition) {
        Student student = studentService.findStudentById(studentRef);
        if(condition == null) {
            return missingDAO.getMissingByStudent(student);
        } else {
            if(condition.equalsIgnoreCase("yes")
                    || condition.equalsIgnoreCase("y")) {
                return missingDAO.getStudentJustifiedMissing(student);

            } else if (condition.equalsIgnoreCase("no")
                    || condition.equalsIgnoreCase("n")) {
                return missingDAO.getStudentNonJustifiedMissingThisMonth(student);

            } else {
                return missingDAO.getMissingByStudent(student);
            }
        }
    }

    @Override
    public List<MissingMatcher> addJustifiedMissing(List<String> std, MissingDTO missingDTO, String responsibleRef) {
        std = std.stream().filter(studentService::checkIfStudentExists).toList();

        Teacher responsible = teacherService.findTeacherById(responsibleRef);
        Course course = courseService.getCourseByName(missingDTO.getCourseName());

        List<MissingMatcher> result = new ArrayList<>();

        for(String stdRef : std) {
            Student student = studentService.findStudentById(stdRef);
            Missing missing = missingDAO.addMissing(
                    stdRef,
                    course,
                    responsible,
                    missingDTO.getCommencement(),
                    missingDTO.getTermination(),
                    true
            );
            result.add(new MissingMatcher(student, List.of(missing)));
        }
        return result;
    }

    @Override
    public List<MissingMatcher> addUnjustifiedMissing(List<String> std, MissingDTO missingDTO, String responsibleRef) {
        std = std.stream().filter(studentService::checkIfStudentExists).toList();

        Teacher responsible = teacherService.findTeacherById(responsibleRef);
        Course course = courseService.getCourseByName(missingDTO.getCourseName());

        List<MissingMatcher> result = new ArrayList<>();

        for(String stdRef : std) {
            Student student = studentService.findStudentById(stdRef);
            Missing missing = missingDAO.addMissing(
                    stdRef,
                    course,
                    responsible,
                    missingDTO.getCommencement(),
                    missingDTO.getTermination(),
                    false
            );
            result.add(new MissingMatcher(student, List.of(missing)));
        }
        return result;
    }

    public MissingMatcher findAllStudentMissingByCourse(String studentRef, String courseName, String condition) {
        Student student = studentService.findStudentById(studentRef);
        MissingMatcher res = null;
        if(condition != null) {
            if(condition.equalsIgnoreCase("yes")
                    || condition.equalsIgnoreCase("y")) {
                res = missingDAO.getStudentJustifiedMissing(student);
                List<Missing> missing = res.getAttendances()
                        .stream().filter(mis -> mis.getCourse().getName().equalsIgnoreCase(courseName))
                        .toList();
                res.setAttendances(missing);
                return res;
            } else if (condition.equalsIgnoreCase("no")
                    || condition.equalsIgnoreCase("n")) {
                res = missingDAO.getStudentNonJustifiedMissing(student);
                List<Missing> missing = res.getAttendances()
                        .stream().filter(mis -> mis.getCourse().getName().equalsIgnoreCase(courseName))
                        .toList();
                res.setAttendances(missing);

                return res;
            } else {
                return missingDAO.getStudentMissingByCourse(student, courseName);
            }
        } else {
            return missingDAO.getStudentMissingByCourse(student, courseName);
        }
    }

    @Override
    public MissingMatcher findStudentMissingByCourse(
            String studentRef,
            String courseName,
            Integer month,
            Integer year,
            String condition
    ) {
        MissingMatcher res = findAllStudentMissingByCourse(studentRef, courseName, condition);
        List<Missing> missing = res.getAttendances();
        if(month == null && year == null) {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== LocalDateTime.now().getMonthValue()
                                    && mis.getCommencement().getYear() == LocalDateTime.now().getYear()
                    ).toList();
            res.setAttendances(missing);

        } else if (month == null) {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== LocalDateTime.now().getMonthValue()
                                    && mis.getCommencement().getYear() == year
                    ).toList();
            res.setAttendances(missing);
        } else if (year == null) {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== month
                                    && mis.getCommencement().getYear() == LocalDateTime.now().getYear()
                    ).toList();
            res.setAttendances(missing);
        } else {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== month
                                    && mis.getCommencement().getYear() == year
                    ).toList();
        }
        res.setAttendances(missing);
        return res;
    }
}
