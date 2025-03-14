package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.dao.MissingDAO;
import dev.razafindratelo.trackmyclass.dto.GeneralMissingDTO;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
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
        List<String> studentRef = studentService.findAllStudentRef();
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
    public List<MissingMatcher> addMissing(
            List<String> missingSTDs,
            GeneralMissingDTO generalMissingDTO,
            boolean isJustified
    ) {
        String levelYear = courseService.getCourseLevelYear(generalMissingDTO.getCourseName()).toString();

        missingSTDs = missingSTDs
                .stream()
                .filter(std -> studentService.checkIfStudentExistsByLevelYear(levelYear, std)).toList();

        Teacher responsible = teacherService.findTeacherById(generalMissingDTO.getResponsibleRef());
        Course course = courseService.getCourseByName(generalMissingDTO.getCourseName());

        List<MissingMatcher> result = new ArrayList<>();

        for(String stdRef : missingSTDs) {
            Student student = studentService.findStudentById(stdRef);
            Missing missing = missingDAO.addMissing(
                    stdRef,
                    course,
                    responsible,
                    generalMissingDTO.getCommencement(),
                    generalMissingDTO.getTermination(),
                    isJustified
            );
            result.add(new MissingMatcher(student, List.of(missing)));
        }
        return result;
    }

    @Override
    public List<MissingMatcher> completeMissing(
            GeneralMissingDTO generalMissingDTO
    ) {
        List<MissingMatcher> missingMatchers = new ArrayList<>();

        missingMatchers.addAll(addMissing(generalMissingDTO.getStdsWithMissingJustification(), generalMissingDTO, true));
        missingMatchers.addAll(addMissing(generalMissingDTO.getStdsWithoutMissingJustification(), generalMissingDTO, false));

        return missingMatchers;
    }

    @Override
    public MissingMatcher updateMissing(
            String courseName,
            LocalDateTime commencement,
            LocalDateTime termination,
            MissingDTO missingDTO
    ) {
        if(missingDTO == null)
            throw new IllegalRequestException("Request body for updating missing must not be null");


        if(missingDTO.getCommencement() == null || missingDTO.getTermination() == null
                || missingDTO.getResponsibleRef() == null || missingDTO.getStudentRef() == null
        )
            throw new IllegalRequestException("The body attributes must not be null");


        Teacher responsible = teacherService.findTeacherById(missingDTO.getResponsibleRef());
        Course course = courseService.getCourseByName(courseName);
        Student student = studentService.findStudentById(missingDTO.getStudentRef());

        Missing missing = new Missing(
                missingDTO.getCommencement(),
                missingDTO.getTermination(),
                responsible,
                course,
                missingDTO.isJustified()
        );
        Missing updatedMissing = missingDAO.updateMissingDateAndJustification(
                missingDTO.getStudentRef(),
                commencement,
                termination,
                missing
        );

        return new MissingMatcher(student, List.of(updatedMissing));
    }

    @Override
    public Missing deleteMissing(String courseName, String studentRef, LocalDateTime commencement, LocalDateTime termination, String responsibleRef) {
        if(courseName ==null || studentRef == null || commencement == null || termination == null)
            throw new IllegalRequestException("Parameters must not be null");
        Course course = courseService.getCourseByName(courseName);
        Student student = studentService.findStudentById(studentRef);
        Teacher responsible = teacherService.findTeacherById(responsibleRef);
        Missing msg = missingDAO.getMissingByStudent(student).getAttendances().stream().filter(
                ms -> ms.getCourse().equals(course) && ms.getCommencement().equals(commencement)
                && ms.getTermination().equals(termination))
                .findFirst().orElse(null);

        if(msg == null)
            return new Missing(LocalDateTime.now(), LocalDateTime.now(), responsible, course);

        MissingDTO missing = new MissingDTO(studentRef, responsibleRef, commencement, termination, msg.isJustified());

        if(missingDAO.deleteMissing(missing, courseName))
            return msg;
        return null;
    }

    @Override
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
