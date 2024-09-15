package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.dto.StudentDTO;
import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.mapper.CourseMapper;
import dev.razafindratelo.trackmyclass.mapper.StudentMapper;
import dev.razafindratelo.trackmyclass.mapper.TeacherMapper;
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
public class DelayDAO {
    private final DBConnection dbConnection;

    public DelayDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<DelayMatcher> getAllDelays() {
        List<DelayMatcher> delays = new ArrayList<>();
        try {
            PreparedStatement getAll = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                    SELECT
                                        student.std_ref,
                                        student.last_name student_last_name,
                                        student.first_name student_first_name,
                                        student.email student_email,
                                        student.phone_number student_phone_number,
                                        student.level_year,
                                        student.group,
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
                                    FROM student INNER JOIN is_delayed
                                        ON student.std_ref = is_delayed.std_ref
                                    INNER JOIN teacher
                                        ON is_delayed.tch_ref = teacher.tch_ref
                                    INNER JOIN course
                                        ON course.crs_ref = is_delayed.crs_ref
                                """
                    );
            getAll.execute();
            ResultSet resultSet = getAll.getResultSet();

            while(resultSet.next()) {
                StudentDTO student = StudentMapper.mapToStudentDTO(resultSet);
                TeacherDTO teacher = TeacherMapper.mapToTeacherDTO(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);

                delays.add(
                        new DelayMatcher(
                                student,
                                new Delay(
                                        resultSet.getObject("commencement", LocalDateTime.class),
                                        resultSet.getObject("termination", LocalDateTime.class),
                                        teacher,
                                        course,
                                        resultSet.getObject("lateness", LocalDateTime.class)
                                )
                        )
                );
            }

        } catch(SQLException e) {
            System.out.println("Error while retrieving delays : " + e.getMessage());
        }
        return delays;
    }
}
