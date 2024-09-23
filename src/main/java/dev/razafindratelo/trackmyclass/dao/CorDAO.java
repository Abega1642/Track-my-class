package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import dev.razafindratelo.trackmyclass.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
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
                LocalDateTime corDate = resultSet.getTimestamp("cor_date").toLocalDateTime();
                Student student = StudentMapper.mapToStudent(resultSet);

                cors.add(new Cor(corRef, student, corDate));
            }

            return cors;

        } catch (SQLException e) {
            throw new InternalException("Error while retrieving all CORs := " + e.getMessage());
        }
    }

    public Cor addCor(Cor cor) {
        try {
            PreparedStatement insertion = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                     INSERT INTO cor (cor_ref, std_ref, cor_date) VALUES (?,?,?)
                                 """
                    );
            insertion.setString(1, cor.getCorRef());
            insertion.setString(2, cor.getStudent().getUserRef());
            insertion.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            insertion.execute();
            cor.setCorDate(LocalDateTime.now());
            return cor;

        } catch (SQLException e) {
            throw new InternalException("Error while adding COR := " + e.getMessage());
        }
    }


    public Cor deleteCor (Cor cor) {
        try {
            PreparedStatement deletion = dbConnection
                    .getConnection()
                    .prepareStatement("DELETE FROM cor WHERE cor_ref = ?");

            deletion.setString(1, cor.getCorRef());

            deletion.execute();

            return cor;

        } catch (SQLException e) {
            throw new InternalException("Error while deleting cor with id " + cor.getCorRef() + " := " + e.getMessage());
        }
    }
}
