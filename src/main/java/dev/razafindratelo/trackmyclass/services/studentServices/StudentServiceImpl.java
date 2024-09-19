package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.BadRequestException;
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
            throw new BadRequestException("Student attributes must not be null");
        }
        return studentDAO.addStudent(student);
    }
}
