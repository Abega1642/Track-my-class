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

@Repository
@AllArgsConstructor
public class TeacherDAO {
    private final DBConnection dbConnection;

    public Teacher findTeacherById(String teacherRef) {
        Teacher teacher = null;
        try {
            PreparedStatement getTeacher = dbConnection
                    .getConnection()
                    .prepareStatement("SELECT * FROM teacher WHERE tch_ref = ?");
            getTeacher.setString(1, teacherRef);

            ResultSet res = getTeacher.executeQuery();

            if(res.next()) {
                teacher = TeacherMapper.mapToTeacher(res);
            }

        } catch (SQLException e) {
            throw new InternalException("Error while finding teacher with id " + teacherRef +" := " + e.getMessage());
        }
        return teacher;
    }
}
