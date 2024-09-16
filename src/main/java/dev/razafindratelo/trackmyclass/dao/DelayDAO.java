package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.mapper.CourseMapper;
import dev.razafindratelo.trackmyclass.mapper.StudentMapper;
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
@Getter
@AllArgsConstructor
public class DelayDAO {
    private final DBConnection dbConnection;

    public DelayMatcher getDelaysByStudent(Student student) {
        List<Delay> delays = new ArrayList<>();
        DelayMatcher delayMatcher = new DelayMatcher(student, List.of());
        try {
            PreparedStatement getDelays = dbConnection.getConnection()
                    .prepareStatement(
                            """
                                SELECT
                                        course.crs_ref,
                                        course.name course_name,
                                        is_delayed.commencement,
                                        is_delayed.lateness,
                                        is_delayed.termination,
                                        teacher.tch_ref,
                                        teacher.last_name teacher_last_name,
                                        teacher.first_name teacher_first_name,
                                        teacher.is_assistant,
                                        teacher.email teacher_email,
                                        teacher.phone_number teacher_phone_number
                                    FROM teacher INNER JOIN is_delayed
                                        ON is_delayed.tch_ref = teacher.tch_ref
                                    INNER JOIN course
                                        ON course.crs_ref = is_delayed.crs_ref
                                    WHERE is_delayed.std_ref = ?
                                """
                    );
            getDelays.setString(1, student.getUserRef());
            getDelays.execute();

            ResultSet resultSet = getDelays.getResultSet();

            while (resultSet.next()) {
                TeacherDTO teacher = TeacherMapper.mapToTeacherDTO(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);

                delays.add(
                        new Delay(
                                resultSet.getObject("commencement", LocalDateTime.class),
                                resultSet.getObject("termination", LocalDateTime.class),
                                teacher,
                                course,
                                resultSet.getObject("lateness", LocalDateTime.class)
                        )
                );
            }
            delayMatcher.setDelay(delays);
        } catch (SQLException e) {
            System.out.println("Error while retrieving delays by student ref: " + e.getMessage());
        }
        return delayMatcher;
    }
}
