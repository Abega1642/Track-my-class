package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.AttendanceMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
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
        List<String> stds = getAllStudentRef();
        for (String std : stds) {
            Student student = getStudent(std);
            attendances.add(getAttendanceByStudent(student));
        }
        return attendances;
    }

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
                TeacherDTO teacher = TeacherMapper.mapToTeacherDTO(resultSet);
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

            attendanceMatcher.setAttendance(attendances);
        } catch(SQLException e) {
            System.out.println("Error while retrieving attendances by student ref: " + e.getMessage());
        }
        return attendanceMatcher;
    }

    public Student getStudent(String std) {
        Student student = null;
        try {
            PreparedStatement getStudent = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                SELECT * FROM student
                                WHERE std_ref = ?
                                """
                    );
            getStudent.setString(1, std);
            ResultSet resultSet = getStudent.executeQuery();
            if (resultSet.next()) {
                student = StudentMapper.mapToStudent2(resultSet);
            }

        } catch (SQLException e) {
            System.out.println("Error while retrieving student : " + e.getMessage());
        }
        return student;
    }

    public List<String> getAllStudentRef() {
        List<String> studentRefs = new ArrayList<>();
        try {
            PreparedStatement getAllSTDs = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                    SELECT std_ref FROM student
                                """
                    );
            getAllSTDs.execute();
            ResultSet resultSet = getAllSTDs.getResultSet();
            while (resultSet.next()) {
                studentRefs.add(resultSet.getString("std_ref"));
            }

        } catch(SQLException e) {
            System.out.println("Error while retrieving students ref: " + e.getMessage());
        }
        return studentRefs;
    }
}
