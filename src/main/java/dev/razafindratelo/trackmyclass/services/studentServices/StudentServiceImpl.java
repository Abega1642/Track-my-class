package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.mergers.StudentMerger;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceDuplicatedException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Getter
public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO;
    private StudentMerger studentMerger;

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

    @Override
    public Student deleteStudent(String std) {
        if(std == null) {
            throw new IllegalRequestException("STD must not be null");
        }
        Student student = studentDAO.deleteStudent(std);
        return Objects.requireNonNullElseGet(student, () -> new Student(
                "-",
                "NO_MATCH",
                "NO_MATCH",
                "NO_MATCH",
                "NO_MATCH",
                null,
                null
        ));
    }

    @Override
    public Student updateStudentIntegrally(String std, Student student) {
        if (student.getLastName() == null
                || student.getEmail() == null
                || student.getLevel() == null
                || student.getGroup() == null
                || student.getPhoneNumber() == null
        ) {
            throw new IllegalRequestException("Student attributes must not be null");
        }
        return studentDAO.integralUpdateStudent(std, student);
    }

    @Override
    public Student updateStudentPartially(String std, Student student)
            throws NoSuchFieldException, IllegalAccessException {

        Student studentToBeUpdated = findStudentById(std);
        if(student == null) {
            return studentToBeUpdated;
        }
        studentMerger.mergeFields(student, studentToBeUpdated);

        return studentDAO.integralUpdateStudent(std, studentToBeUpdated);
    }
}
