package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    Student findStudentById(String std);
}
