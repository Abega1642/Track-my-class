package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import dev.razafindratelo.trackmyclass.mapper.StudentMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CorDAO {
    private DBConnection dbConnection;

    public List<Cor> getAllCors() {
        List<Cor> cors = new ArrayList<>();
        try {
            PreparedStatement getAll = dbConnection
                            .getConnection()
                            .prepareStatement("""
                                                    SELECT 
                                                       cor_ref,
                                                       student.*
                                                     FROM cor INNER JOIN student
                                                       ON cor.std_ref = student.std_ref
                                                   """);
            getAll.execute();
            ResultSet resultSet = getAll.getResultSet();
            while (resultSet.next()) {

                String corRef = resultSet.getString("cor_ref");
                Student student = StudentMapper.mapToStudent(resultSet);

                cors.add(new Cor(corRef, student));
            }

            return cors;

        } catch (SQLException e) {
            throw new InternalException("Error while retrieving all CORs := " + e.getMessage());
        }
    }
}
