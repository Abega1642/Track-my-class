package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import dev.razafindratelo.trackmyclass.mapper.TeacherMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TeacherDAO {
    private final DBConnection dbConnection;

    public Teacher findTeacherById(String teacherRef) {
        Teacher teacher = null;
        try {
            PreparedStatement getTeacher = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                        SELECT
                                            tch_ref,
                                            teacher.last_name teacher_last_name,
                                            teacher.first_name teacher_first_name,
                                            teacher.email teacher_email,
                                            teacher.phone_number teacher_phone_number,
                                            is_assistant
                                        FROM teacher WHERE tch_ref = ?
                                    """
                    );

            getTeacher.setString(1, teacherRef);

            ResultSet resultSet = getTeacher.executeQuery();
            if (resultSet.next()) {
                teacher = TeacherMapper.mapToTeacher(resultSet);
            }

        } catch (SQLException e) {
            throw new InternalException("Error while finding teacher with id " + teacherRef + " := " + e.getMessage());
        }
        return teacher;
    }

    public List<Teacher> findAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            PreparedStatement getTeachers = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                        SELECT
                                            tch_ref,
                                            teacher.last_name teacher_last_name,
                                            teacher.first_name teacher_first_name,
                                            teacher.email teacher_email,
                                            teacher.phone_number teacher_phone_number,
                                            is_assistant
                                        FROM teacher
                                    """
                    );

            getTeachers.execute();
            ResultSet resultSet = getTeachers.getResultSet();
            while (resultSet.next()) {
                Teacher teacher = TeacherMapper.mapToTeacher(resultSet);
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            throw new InternalException("Error while retrieving all teachers := " + e.getMessage());
        }
        return teachers;
    }

    public Teacher addTeacher(Teacher teacher) {
        try {
            PreparedStatement insertion = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                    INSERT INTO Teacher (
                                        tch_ref,
                                        last_name,
                                        first_name,
                                        is_assistant,
                                        email,
                                        phone_number
                                    ) VALUES (?,?,?,?,?,?)
                                """
                    );
            insertion.execute();
            if(insertion.executeUpdate() > 1) {
                return teacher;
            }
        } catch(SQLException e) {
            throw new InternalException("Error while adding teacher := " + e.getMessage());
        }
        return null;
    }

    public Teacher integralUpdateTeacher(String teacherRef, Teacher teacher) {
        try {
            PreparedStatement update = dbConnection.getConnection()
                    .prepareStatement(
                            """
                                     UPDATE Teacher SET
                                         last_name = ?,
                                         first_name = ?,
                                         is_assistant = ?,
                                         phone_number = ?,
                                         email = ?
                                    WHERE tch_ref = ?
                                 """
                    );
            update.setString(1, teacher.getLastName());
            update.setString(2, teacher.getFirstName());
            update.setBoolean(3, teacher.isAssistant());
            update.setString(4, teacher.getPhoneNumber());
            update.setString(5, teacher.getEmail());
            update.setString(6, teacherRef);

            update.executeUpdate();

            teacher.setUserRef(teacherRef);
            return teacher;

        } catch(SQLException e) {
            throw new InternalException("Error while updating teacher := " + e.getMessage());
        }
    }
}
