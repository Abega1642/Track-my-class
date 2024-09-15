package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
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
public class AttendanceDAO {
    private final DBConnection dbConnection;

    public AttendanceDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<AttendanceMatcher> getAllAttendance() {
        List<AttendanceMatcher> attendances = new ArrayList<>();
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
                                    FROM student INNER JOIN is_present
                                        ON student.std_ref = is_present.std_ref
                                    INNER JOIN teacher
                                        ON is_present.tch_ref = teacher.tch_ref
                                    INNER JOIN course
                                        ON course.crs_ref = is_present.crs_ref
                                """);
            getAll.execute();
            ResultSet resultSet = getAll.getResultSet();
            while (resultSet.next()) {
                Student student = StudentMapper.mapToStudent(resultSet);
                Teacher teacher = TeacherMapper.mapToTeacher(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);
                attendances.add(
                        new AttendanceMatcher(
                            student,
                            new Attendance(
                                    resultSet.getObject("commencement", LocalDateTime.class),
                                    resultSet.getObject("termination", LocalDateTime.class),
                                    teacher,
                                    course
                            )
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving attendances : " + e.getMessage());
        }
        return attendances;
    }

    public List<AttendanceMatcher> getAttendanceByStudentId(String studentId) {
        List<AttendanceMatcher> attendances = new ArrayList<>();
        try {
            PreparedStatement getById = dbConnection.getConnection()
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
                                    FROM student INNER JOIN is_present
                                        ON student.std_ref = is_present.std_ref
                                    INNER JOIN teacher
                                        ON is_present.tch_ref = teacher.tch_ref
                                    INNER JOIN course
                                        ON course.crs_ref = is_present.crs_ref
                                    WHERE student.std_ref = ?
                                """);
            getById.setString(1, studentId);
            getById.execute();
            ResultSet resultSet = getById.getResultSet();
            while (resultSet.next()) {
                Student student = StudentMapper.mapToStudent(resultSet);
                Teacher teacher = TeacherMapper.mapToTeacher(resultSet);
                Course course = CourseMapper.mapToCourse(resultSet);

                attendances.add(
                        new AttendanceMatcher(
                                student,
                                new Attendance(
                                    resultSet.getObject("commencement", LocalDateTime.class),
                                    resultSet.getObject("termination", LocalDateTime.class),
                                    teacher,
                                    course
                                )
                        )
                );
            }
        } catch(SQLException e) {
            System.out.println("Error while retrieving attendances by student ref: " + e.getMessage());
        }
        return attendances;
    }

}
