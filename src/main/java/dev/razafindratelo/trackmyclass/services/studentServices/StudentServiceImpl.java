package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.mergers.StudentMerger;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
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
    public List<Student> findAllStudentsByLevelYear(String levelYear) {
        return findAllStudents()
                .stream()
                .filter(s -> s.getLevel().equals(Level.valueOf(levelYear)))
                .toList();
    }

    @Override
    public List<Student> findAllStudentByGroup(String group) {
        return findAllStudents()
                .stream()
                .filter(s -> s.getGroup().equals(Group.valueOf(group)))
                .toList();
    }

    @Override
    public List<String> findAllStudentRef() {
        return studentDAO.getAllStudentRef();
    }

    @Override
    public List<String> findAllStudentRefByLevelYear(String levelYear) {
        return studentDAO.getAllStudentOfSpecificLevel(Level.valueOf(levelYear));
    }

    @Override
    public List<String> findAllStudentRefByGroup(String group) {
        return studentDAO.getAllStudentOfSpecificGroup(Group.valueOf(group));
    }

    @Override
    public boolean checkIfStudentExists(String std) {
        return findAllStudentRef().contains(std);
    }

    @Override
    public boolean checkIfStudentExistsByLevelYear(String levelYear, String std) {
        return findAllStudentRefByLevelYear(levelYear).contains(std);
    }

    @Override
    public boolean checkIfStudentExistsByGroup(String group, String std) {
        return findAllStudentRefByGroup(group).contains(std);
    }

    @Override
    public List<String> filterPresentStdsByLevelYear(List<String> missingSTDs, String levelYear) {
        return findAllStudentRefByLevelYear(levelYear).stream().filter(s -> !missingSTDs.contains(s)).toList();
    }

    @Override
    public List<String> filterPresentStdsByGroup(List<String> missingSTDs, String group) {
        return findAllStudentRefByGroup(group).stream().filter(s -> !missingSTDs.contains(s)).toList();
    }

    @Override
    public List<String> filterExistingStudentByLevelYear(List<String> STDs, String levelYear) {
        return STDs.stream().filter(std -> checkIfStudentExistsByLevelYear(levelYear, std)).toList();
    }

    @Override
    public List<String> filterExistingStudentByGroup(List<String> STDs, String group) {
        return STDs.stream().filter(std -> checkIfStudentExistsByGroup(group, std)).toList();
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
