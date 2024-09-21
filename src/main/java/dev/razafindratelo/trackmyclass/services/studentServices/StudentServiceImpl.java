package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceDuplicatedException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO;

    @Override
    public Student findStudentById(String std) {
        Student student =  studentDAO.getStudentById(std);
        if (student == null) {
            throw new ResourceNotFoundException("Student with id " + std + " not found");
        } else {
            return student;
        }
    }

    @Override
    public List<Student> findAllStudents() {
        return studentDAO.getAllStudent();
    }

    @Override
    public Student insertStudent(Student student) {
        List<String> stds = studentDAO.getAllStudentRef();
        stds.forEach(System.out::println);
        if (stds.contains(student.getUserRef())) {
            throw new ResourceDuplicatedException("This student already exists");
        } else if (
                student.getUserRef() == null
                || student.getLastName() == null
                || student.getEmail() == null
                || student.getLevel() == null
                || student.getGroup() == null
                || student.getPhoneNumber() == null
        ) {
            throw new IllegalRequestException("Student attributes must not be null");
        }
        return studentDAO.addStudent(student);
    }

    @Override
    public List<String> getAllStudentsRef() {
        return studentDAO.getAllStudentRef();
    }

    @Override
    public boolean checkIfStudentExists(String std) {
        return getAllStudentsRef().contains(std);
    }

    @Override
    public List<String> filterExistingStudents(List<String> STDs) {
        return STDs.stream().filter(this::checkIfStudentExists).toList();
    }

    @Override
    public List<String> filterPresentStds(List<String> STDs) {
        return getAllStudentsRef().stream().filter(s -> !STDs.contains(s)).toList();
    }
}
