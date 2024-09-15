package dev.razafindratelo.trackmyclass.dao.repository;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Repository
@Getter
public class DBConnection {
    private final String url = "jdbc:postgresql://localhost:5432/track_my_class";
    private final String user = "postgres";
    private final String password = "razafindratelo";

    private Connection connection;

    public DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println(" +++ Connection to database established successfully +++ ");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection to database failed : " + e.getMessage());
        }
    }

}
