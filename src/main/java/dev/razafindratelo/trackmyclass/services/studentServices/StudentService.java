package dev.razafindratelo.trackmyclass.services.studentServices;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface StudentService {

    Student findStudentById(String std);

    List<Student> findAllStudents();

    List<Student> findAllStudentsByLevelYear(String levelYear);

    List<Student> findAllStudentByGroup(String group);

    List<String> findAllStudentRef();

    List<String> findAllStudentRefByLevelYear(String levelYear);

    List<String> findAllStudentRefByGroup(String group);

    boolean checkIfStudentExists(String std);

    boolean checkIfStudentExistsByLevelYear(String levelYear, String std);

    boolean checkIfStudentExistsByGroup(String group, String std);

    List<String> filterExistingStudentByLevelYear(List<String> STDs, String level);

    List<String> filterExistingStudentByGroup(List<String> STDs, String group);

    List<String> filterPresentStdsByLevelYear(List<String> missingSTDs, String levelYear);

    List<String> filterPresentStdsByGroup(List<String> missingSTDs, String group);

    Student insertStudent(Student student);

    Student deleteStudent(String std);

    Student updateStudentIntegrally(String std, Student student);

    Student updateStudentPartially(String std, Student student) throws NoSuchFieldException, IllegalAccessException;

    List<Student> completeUpgradeStudentsLevels(List<String> excludedSTDs);

}
