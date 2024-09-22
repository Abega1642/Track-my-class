package dev.razafindratelo.trackmyclass.services.delayServices;

import dev.razafindratelo.trackmyclass.dao.DelayDAO;
import dev.razafindratelo.trackmyclass.dto.DelayDTO;
import dev.razafindratelo.trackmyclass.dto.GeneralAttendanceDTO;
import dev.razafindratelo.trackmyclass.dto.StudentDelayDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import dev.razafindratelo.trackmyclass.services.teacherServices.TeacherService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
@AllArgsConstructor
public class DelayServiceImpl implements DelayService {
    private final DelayDAO delayDAO;
    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;

    @Override
    public List<DelayMatcher> findAllDelays() {
        List<String> stds = studentService.findAllStudentRef();
        List<DelayMatcher> delays = new ArrayList<>();

        for (String std : stds) {
            Student student = studentService.findStudentById(std);
            delays.add(findDelaysByStudentRef(std));
        }
        return delays;
    }

    @Override
    public DelayMatcher findDelaysByStudentRef(String studentRef) {
        Student student = studentService.findStudentById(studentRef);
        return delayDAO.getDelaysByStudent(student);
    }

    @Override
    public List<DelayMatcher> completeAddDelay(List<StudentDelayDTO> lateStudentSTDs, GeneralAttendanceDTO generalAttendance) {
        String levelYear = courseService.getCourseLevelYear(generalAttendance.getCourseName()).toString();

        lateStudentSTDs = lateStudentSTDs
                .stream()
                .filter(std -> studentService.checkIfStudentExistsByLevelYear(levelYear, std.getStd())).toList();

        Teacher responsible = teacherService.findTeacherById(generalAttendance.getResponsibleRef());
        Course course = courseService.getCourseByName(generalAttendance.getCourseName());

        return constructDelayMatchers(
                generalAttendance.getCommencement(),
                generalAttendance.getTermination(),
                lateStudentSTDs,
                course,
                responsible
        );
    }

    @Override
    public List<DelayMatcher> addDelay(DelayDTO delayDTO) {

        List<String> STDs = delayDTO.getDelays().
                stream()
                .map(StudentDelayDTO::getStd)
                .toList();

        String levelYear = courseService.getCourseLevelYear(delayDTO.getCourseName()).toString();
        List<String> filteredSTDs = studentService.filterExistingStudentByLevelYear(STDs, levelYear);

        List<StudentDelayDTO> delays = delayDTO.getDelays()
                .stream()
                .filter(delay -> filteredSTDs.contains(delay.getStd()))
                .toList();

        Course course = courseService.getCourseByName(delayDTO.getCourseName());

        Teacher responsible = teacherService.findTeacherById(delayDTO.getResponsibleRef());


        return constructDelayMatchers(
                delayDTO.getCommencement(),
                delayDTO.getTermination(),
                delays,
                course,
                responsible
        );
    }

    public List<DelayMatcher> constructDelayMatchers(
            LocalDateTime commencement,
            LocalDateTime termination,
            List<StudentDelayDTO> delays,
            Course course,
            Teacher responsible
    ) {
        List<DelayMatcher> result = new ArrayList<>();

        for(StudentDelayDTO del : delays) {
            Student student = studentService.findStudentById(del.getStd());

            Delay delay = delayDAO.addDelay(
                    del.getStd(),
                    course,
                    responsible,
                    commencement,
                    termination,
                    del.getLateness()
            );
            result.add(new DelayMatcher(student, List.of(delay)));
        }
        return result;
    }
}
