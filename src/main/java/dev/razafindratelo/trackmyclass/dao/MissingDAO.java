package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.mapper.CourseMapper;
import dev.razafindratelo.trackmyclass.mapper.TeacherMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Getter
public class MissingDAO {
    private final DBConnection dbConnection;

    public MissingMatcher getMissingByStudent(Student student) {
        List<Missing> missings = new ArrayList<>();
        MissingMatcher missingMatcher = new MissingMatcher(student, List.of());
        try {
            PreparedStatement getMissing = dbConnection.getConnection()
                    .prepareStatement(
                            """
                                SELECT
                                        course.crs_ref,
                                        course.name course_name,
                                        has_missed.commencement,
                                        has_missed.termination,
                                        has_missed.is_justified,
                                        teacher.tch_ref,
                                        teacher.last_name teacher_last_name,
                                        teacher.first_name teacher_first_name,
                                        teacher.is_assistant,
                                        teacher.email teacher_email,
                                        teacher.phone_number teacher_phone_number
                                    FROM teacher INNER JOIN has_missed
                                        ON has_missed.tch_ref = teacher.tch_ref
                                    INNER JOIN course
                                        ON course.crs_ref = has_missed.crs_ref
                                    WHERE has_missed.std_ref = ?
                                """
                    );
            getMissing.setString(1, student.getUserRef());
            getMissing.execute();

            ResultSet resultSet = getMissing.getResultSet();

            while (resultSet.next()) {
                TeacherDTO teacher = TeacherMapper.mapToTeacherDTO(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);

                missings.add(
                        new Missing(
                                resultSet.getObject("commencement", LocalDateTime.class),
                                resultSet.getObject("termination", LocalDateTime.class),
                                teacher,
                                course,
                                resultSet.getBoolean("is_justified")
                        )
                );
            }
            missingMatcher.setMissingList(missings);
        } catch (SQLException e) {
            System.out.println("Error while retrieving missing by student ref: " + e.getMessage());
        }
        return missingMatcher;
    }

    public MissingMatcher getStudentMissingByCourse(Student student, String courseName) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        List<Missing> missing = missingMatcher.getMissingList()
                .stream().filter(mis -> mis.getCourse().getName().equalsIgnoreCase(courseName))
                .toList();
        missingMatcher.setMissingList(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentMissingByCourseThisMonth(Student student, String courseName) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        LocalDateTime date = LocalDateTime.now();
        List<Missing> missing = missingMatcher.getMissingList()
                .stream().filter(mis ->
                        mis.getCourse().getName().equalsIgnoreCase(courseName)
                                && checkMissingMonth(mis, date.getMonthValue(), date.getYear()))
                .toList();
        missingMatcher.setMissingList(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentMissingOfThisMonth(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        LocalDateTime date = LocalDateTime.now();
        List<Missing> missing = missingMatcher.getMissingList()
                .stream().filter(
                        mis -> checkMissingMonth(mis, date.getMonthValue(), date.getYear())
                )
                .toList();
        missingMatcher.setMissingList(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentNonJustifiedMissing(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        List<Missing> missing = missingMatcher.getMissingList()
                .stream().filter(mis -> !mis.isJustified())
                .toList();
        missing.forEach(System.out::println);
        missingMatcher.setMissingList(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentJustifiedMissing(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        List<Missing> missing = missingMatcher.getMissingList()
                .stream().filter(Missing::isJustified)
                .toList();
        missing.forEach(System.out::println);
        missingMatcher.setMissingList(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentNonJustifiedMissingThisMonth(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        LocalDateTime date = LocalDateTime.now();
        List<Missing> missing = missingMatcher.getMissingList()
                .stream().filter(mis ->
                        !mis.isJustified()
                        && checkMissingMonth(mis, date.getMonthValue(), date.getYear())
                )
                .toList();
        missingMatcher.setMissingList(missing);
        return missingMatcher;
    }

    private static boolean checkMissingMonth(Missing missing, int month, int year) {
        return missing.getCommencement().getMonthValue() == month && missing.getCommencement().getYear() == year;
    }

}
