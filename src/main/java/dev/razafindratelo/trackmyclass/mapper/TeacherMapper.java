package dev.razafindratelo.trackmyclass.mapper;

import dev.razafindratelo.trackmyclass.dto.TeacherDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper {
    public static TeacherDTO mapToTeacherDTO(ResultSet resultSet) throws SQLException {
        return new TeacherDTO(
                resultSet.getString("tch_ref"),
                resultSet.getString("teacher_last_name"),
                resultSet.getString("teacher_first_name"),
                resultSet.getString("teacher_email"),
                resultSet.getString("teacher_phone_number"),
                resultSet.getBoolean("is_assistant")
        );
    }
}
