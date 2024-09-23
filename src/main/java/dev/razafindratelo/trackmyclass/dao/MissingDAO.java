package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.dto.MissingDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import dev.razafindratelo.trackmyclass.mapper.CourseMapper;
import dev.razafindratelo.trackmyclass.mapper.TeacherMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Getter
public class MissingDAO {
    private final DBConnection dbConnection;

    public MissingMatcher getMissingByStudent(Student student) {
        List<Missing> missing = new ArrayList<>();
        MissingMatcher missingMatcher = new MissingMatcher(student, List.of());
        try {
            PreparedStatement getMissing = dbConnection.getConnection()
                    .prepareStatement(
                            """
                                SELECT
                                        course.crs_ref,
                                        course.name course_name,
                                        course.credit crs_credit,
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
                Teacher teacher = TeacherMapper.mapToTeacher(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);

                missing.add(
                        new Missing(
                                resultSet.getObject("commencement", LocalDateTime.class),
                                resultSet.getObject("termination", LocalDateTime.class),
                                teacher,
                                course,
                                resultSet.getBoolean("is_justified")
                        )
                );
            }
            missingMatcher.setAttendances(missing);
        } catch (SQLException e) {
            throw new InternalException("Error while retrieving missing by student ref: " + e.getMessage());
        }
        return missingMatcher;
    }

    public MissingMatcher getStudentMissingByCourse(Student student, String courseName) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        List<Missing> missing = missingMatcher.getAttendances()
                .stream().filter(mis -> mis.getCourse().getName().equalsIgnoreCase(courseName))
                .toList();
        missingMatcher.setAttendances(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentNonJustifiedMissing(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        List<Missing> missing = missingMatcher.getAttendances()
                .stream().filter(mis -> !mis.isJustified())
                .toList();
        missingMatcher.setAttendances(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentJustifiedMissing(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        List<Missing> missing = missingMatcher.getAttendances()
                .stream().filter(Missing::isJustified)
                .toList();
        missingMatcher.setAttendances(missing);
        return missingMatcher;
    }

    public MissingMatcher getStudentNonJustifiedMissingThisMonth(Student student) {
        MissingMatcher missingMatcher = getMissingByStudent(student);
        LocalDateTime date = LocalDateTime.now();
        List<Missing> missing = missingMatcher.getAttendances()
                .stream().filter(mis ->
                        !mis.isJustified()
                        && checkMissingMonth(mis, date.getMonthValue(), date.getYear())
                )
                .toList();
        missingMatcher.setAttendances(missing);
        return missingMatcher;
    }

    private static boolean checkMissingMonth(Missing missing, int month, int year) {
        return missing.getCommencement().getMonthValue() == month && missing.getCommencement().getYear() == year;
    }

    public Missing addMissing(
            String std,
            Course course,
            Teacher responsible,
            LocalDateTime commencement,
            LocalDateTime termination,
            boolean isJustified
    ) {
        try {
            PreparedStatement insertion = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                     INSERT INTO has_missed (
                                         std_ref,
                                         crs_ref,
                                         tch_ref,
                                         commencement,
                                         termination,
                                         is_justified
                                     ) VALUES (?,?,?,?,?,?)
                                 """
                    );
            insertion.setString(1, std);
            insertion.setString(2, course.getCourseRef());
            insertion.setString(3, responsible.getUserRef());
            insertion.setTimestamp(4, Timestamp.valueOf(commencement));
            insertion.setTimestamp(5, Timestamp.valueOf(termination));
            insertion.setBoolean(6, isJustified);

            insertion.execute();

            return new Missing(
                    commencement,
                    termination,
                    responsible,
                    course,
                    isJustified
            );

        } catch(SQLException e) {
            throw new InternalException("Error while adding missing: " + e.getMessage());
        }
    }

    public Missing updateMissingDateAndJustification(
            String std,
            LocalDateTime commencement,
            LocalDateTime termination,
            Missing missing
    ) {
        try {
            PreparedStatement update = dbConnection
                    .getConnection()
                    .prepareStatement("""
                                               UPDATE has_missed SET
                                                   commencement = ?,
                                                   termination = ?,
                                                   is_justified = ?
                                               WHERE
                                                   std_ref = ? AND
                                                   crs_ref = ? AND
                                                   tch_ref = ? AND
                                                   commencement = ? AND
                                                   termination = ?
                                           """
                    );
            update.setTimestamp(1, Timestamp.valueOf(missing.getCommencement()));
            update.setTimestamp(2, Timestamp.valueOf(missing.getTermination()));
            update.setBoolean(3, missing.isJustified());

            update.setString(4, std);
            update.setString(5, missing.getCourse().getCourseRef());
            update.setString(6, missing.getAttendanceResponsible().getUserRef());
            update.setTimestamp(7, Timestamp.valueOf(commencement));
            update.setTimestamp(8, Timestamp.valueOf(termination));

            update.executeUpdate();

            return missing;


        } catch(SQLException e) {
            throw new InternalException("Error while updating missing: " + e.getMessage());
        }
    }

    public boolean deleteMissing(
            MissingDTO missingDTO,
            String courseName
    ) {
        try {
            PreparedStatement deletion = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                 DELETE FROM has_missed WHERE 
                                     std_ref = ? AND
                                     tch_ref = ? AND
                                     commencement = ? AND
                                     termination = ? AND
                                     crs_ref = ? AND
                                 """
                    );
            deletion.setString(1, missingDTO.getStudentRef());
            deletion.setString(2, missingDTO.getResponsibleRef());
            deletion.setTimestamp(3, Timestamp.valueOf(missingDTO.getCommencement()));
            deletion.setTimestamp(4, Timestamp.valueOf(missingDTO.getTermination()));
            deletion.setString(5, courseName);

            deletion.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new InternalException("Error while deleting missing: " + e.getMessage());
        }
    }

}
