package dev.razafindratelo.trackmyclass.mapper;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper {
    public static Student mapToStudent(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getString("std_ref"),
                resultSet.getString("student_last_name"),
                resultSet.getString("student_first_name"),
                resultSet.getString("student_email"),
                resultSet.getString("student_phone_number"),
                Level.valueOf(resultSet.getString("level_year")),
                Group.valueOf(resultSet.getString("group"))
        );
    }

    public static Student mapToStudent2(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getString("std_ref"),
                resultSet.getString("last_name"),
                resultSet.getString("first_name"),
                resultSet.getString("email"),
                resultSet.getString("phone_number"),
                Level.valueOf(resultSet.getString("level_year")),
                Group.valueOf(resultSet.getString("group"))
        );
    }
}
