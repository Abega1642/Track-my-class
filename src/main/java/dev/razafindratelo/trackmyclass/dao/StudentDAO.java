package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
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
                student = StudentMapper.mapToStudent(res);
            }

        } catch(SQLException e) {
            throw new InternalException("Error while finding student by id := " + studentId + " = " + e.getMessage());
        }
        return student;
    }

    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement getAllSTDs = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                    SELECT * FROM student
                                """
                    );
            getAllSTDs.execute();
            ResultSet resultSet = getAllSTDs.getResultSet();
            while (resultSet.next()) {
                var student = StudentMapper.mapToStudent(resultSet);
                students.add(student);
            }

        } catch(SQLException e) {
            System.out.println("Error while retrieving students ref: " + e.getMessage());
        }
        return students;
    }

    public Student addStudent(Student student) {
        try {
            PreparedStatement insert = dbConnection.getConnection()
                    .prepareStatement(
                            """
                                INSERT INTO Student (
                                    std_ref, 
                                    last_name, 
                                    first_name, 
                                    email, 
                                    phone_number, 
                                    level_year, 
                                    "group"
                                ) VALUES
                                  (?,?,?,?,?,?,?)
                                """);
            insert.setString(1, student.getUserRef());
            insert.setString(2, student.getLastName());
            insert.setString(3, student.getFirstName());
            insert.setString(4, student.getEmail());
            insert.setString(5, student.getPhoneNumber());
            insert.setString(6, student.getLevel().toString());
            insert.setString(7, student.getGroup().toString());

            insert.execute();
            return student;

        } catch( SQLException e) {
            throw new InternalException("Error while adding student := " + e.getMessage());
        }
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
