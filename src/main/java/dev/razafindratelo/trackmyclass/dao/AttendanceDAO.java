package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
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
@Getter
@AllArgsConstructor
public class AttendanceDAO {
    private final DBConnection dbConnection;

    public AttendanceMatcher getAttendanceByStudent(Student student) {

        List<Attendance> attendances = new ArrayList<>();
        AttendanceMatcher attendanceMatcher = new AttendanceMatcher(student, List.of());

        try {
            PreparedStatement getById = dbConnection.getConnection()
                    .prepareStatement(
                            """
                                    SELECT
                                        is_present.std_ref,
                                        is_present.commencement,
                                        course.crs_ref,
                                        course.name course_name,
                                        is_present.termination,
                                        teacher.tch_ref,
                                        teacher.last_name teacher_last_name,
                                        teacher.first_name teacher_first_name,
                                        teacher.is_assistant,
                                        teacher.email teacher_email,
                                        teacher.phone_number teacher_phone_number
                                    FROM teacher INNER JOIN is_present
                                        ON is_present.tch_ref = teacher.tch_ref
                                    INNER JOIN course
                                        ON course.crs_ref = is_present.crs_ref
                                    WHERE is_present.std_ref = ?
                                """);

            getById.setString(1, student.getUserRef());
            getById.execute();
            ResultSet resultSet = getById.getResultSet();

            while (resultSet.next()) {
                Teacher teacher = TeacherMapper.mapToTeacherDTO(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);

                attendances.add(
                        new Attendance(
                                resultSet.getObject("commencement", LocalDateTime.class),
                                resultSet.getObject("termination", LocalDateTime.class),
                                teacher,
                                course
                        )
                );
            }

            attendanceMatcher.setAttendances(attendances);
        } catch(SQLException e) {
            System.out.println("Error while retrieving attendances by student ref: " + e.getMessage());
        }
        return attendanceMatcher;
    }

}
