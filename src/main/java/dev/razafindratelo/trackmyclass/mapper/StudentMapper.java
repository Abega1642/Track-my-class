package dev.razafindratelo.trackmyclass.mapper;

import dev.razafindratelo.trackmyclass.dto.StudentDTO;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper {
    public static StudentDTO mapToStudentDTO(ResultSet resultSet) throws SQLException {
        return new StudentDTO(
                resultSet.getString("std_ref"),
                resultSet.getString("student_last_name"),
                resultSet.getString("student_first_name"),
                resultSet.getString("student_email"),
                resultSet.getString("student_phone_number"),
                Level.valueOf(resultSet.getString("level_year")),
                Group.valueOf(resultSet.getString("group"))
        );
    }
}
