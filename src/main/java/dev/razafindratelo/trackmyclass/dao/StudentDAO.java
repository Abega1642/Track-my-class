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
import java.util.ArrayList;
import java.util.List;

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
