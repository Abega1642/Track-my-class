package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.dao.MissingDAO;
import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MissingServiceImpl implements MissingService {
    private final MissingDAO missingDAO;
    private final StudentDAO studentDAO;

    @Override
    public List<MissingMatcher> findAllMissing() {
        List<String> studentRef = studentDAO.getAllStudentRef();
        List<MissingMatcher> missingList = new ArrayList<>();

        for (String std : studentRef) {
            Student student = studentDAO.getStudentById(std);
            missingList.add(missingDAO.getMissingByStudent(student));
        }
        return missingList;
    }

    @Override
    public MissingMatcher findMissingByStudentRef(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getMissingByStudent(student);
    }

    @Override
    public MissingMatcher findStudentMissingByCourseRef(String studentRef, String courseName) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getStudentMissingByCourse(student, courseName);
    }

    @Override
    public MissingMatcher findStudentMissingByCourseRefThisMonth(String studentRef, String courseName) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getStudentMissingByCourseThisMonth(student, courseName);
    }

    @Override
    public MissingMatcher findNonJustifiedMissingByStudentRef(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getStudentNonJustifiedMissing(student);
    }

    @Override
    public MissingMatcher findMissingByStudentRefThisMonth(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getStudentMissingOfThisMonth(student);
    }

    @Override
    public MissingMatcher findNonJustifiedMissingByStudentRefThisMonth(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getStudentNonJustifiedMissingThisMonth(student);
    }

    @Override
    public MissingMatcher findJustifiedMissingByStudentRef(String studentRef) {
        Student student = studentDAO.getStudentById(studentRef);
        return missingDAO.getStudentJustifiedMissing(student);
    }

}
