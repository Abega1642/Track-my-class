package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student findStudentById(String std);

    List<Student> findAllStudents();

    Student insertStudent(Student student);

    List<String> getAllStudentsRef();

    boolean checkIfStudentExists(String std);

    List<String> filterExistingStudents(List<String> STDs);

    List<String> filterPresentStds(List<String> STDs);
}
