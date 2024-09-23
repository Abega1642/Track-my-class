package dev.razafindratelo.trackmyclass.dao.repository;

import io.github.cdimascio.dotenv.Dotenv;
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
    private final String url;
    private final String user;
    private final String password;

    private Connection connection;

    public DBConnection() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.user = dotenv.get("DB_USER");
        this.password = dotenv.get("DB_PASSWORD");

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
