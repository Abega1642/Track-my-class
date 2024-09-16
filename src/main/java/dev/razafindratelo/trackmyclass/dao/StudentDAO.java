package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Getter
@AllArgsConstructor
public class StudentDAO {
    private DBConnection dbConnection;

    public Student getStudentById(String studentId) {
        Student student = null;
        try {
            PreparedStatement findStudent = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                SELECT * FROM student WHERE std_ref = ?
                                """
                    );
            findStudent.setString(1, studentId);
            ResultSet res = findStudent.executeQuery();

            if(res.next()) {
                student = StudentMapper.mapToStudent2(res);
            }

        } catch(SQLException e) {
            System.out.println("Error while finding student by id: " + studentId + " : " + e.getMessage());
        }
        return student;
    }
}
