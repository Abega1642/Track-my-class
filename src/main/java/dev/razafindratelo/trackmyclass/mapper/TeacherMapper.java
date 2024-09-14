package dev.razafindratelo.trackmyclass.mapper;

import dev.razafindratelo.trackmyclass.entity.users.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherMapper {
    public static Teacher mapToTeacher(ResultSet resultSet) throws SQLException {
        return new Teacher(
                resultSet.getString("tch_ref"),
                resultSet.getString("teacher_last_name"),
                resultSet.getString("teacher_first_name"),
                resultSet.getString("teacher_email"),
                resultSet.getString("teacher_phone_number"),
                resultSet.getBoolean("is_assistant"),
                List.of()
        );
    }
}
