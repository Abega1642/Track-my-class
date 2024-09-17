package dev.razafindratelo.trackmyclass.services.missingServices;

import dev.razafindratelo.trackmyclass.dao.MissingDAO;
import dev.razafindratelo.trackmyclass.dao.StudentDAO;
import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public MissingMatcher findMissingByStudent(String studentRef, String condition) {
        Student student = studentDAO.getStudentById(studentRef);
        if(condition == null) {
            return missingDAO.getMissingByStudent(student);
        } else {
            if(condition.equalsIgnoreCase("yes")
                    || condition.equalsIgnoreCase("y")) {
                return missingDAO.getStudentJustifiedMissing(student);

            } else if (condition.equalsIgnoreCase("no")
                    || condition.equalsIgnoreCase("n")) {
                return missingDAO.getStudentNonJustifiedMissingThisMonth(student);

            } else {
                return missingDAO.getMissingByStudent(student);
            }
        }
    }

    public MissingMatcher findAllStudentMissingByCourse(String studentRef, String courseName, String condition) {
        Student student = studentDAO.getStudentById(studentRef);
        MissingMatcher res = null;
        if(condition != null) {
            if(condition.equalsIgnoreCase("yes")
                    || condition.equalsIgnoreCase("y")) {
                res = missingDAO.getStudentJustifiedMissing(student);
                List<Missing> missing = res.getMissingList()
                        .stream().filter(mis -> mis.getCourse().getName().equalsIgnoreCase(courseName))
                        .toList();
                res.setMissingList(missing);
                return res;
            } else if (condition.equalsIgnoreCase("no")
                    || condition.equalsIgnoreCase("n")) {
                res = missingDAO.getStudentNonJustifiedMissing(student);
                List<Missing> missing = res.getMissingList()
                        .stream().filter(mis -> mis.getCourse().getName().equalsIgnoreCase(courseName))
                        .toList();
                res.setMissingList(missing);

                return res;
            } else {
                return missingDAO.getStudentMissingByCourse(student, courseName);
            }
        } else {
            return missingDAO.getStudentMissingByCourse(student, courseName);
        }
    }

    @Override
    public MissingMatcher findStudentMissingByCourse(
            String studentRef,
            String courseName,
            Integer month,
            Integer year,
            String condition
    ) {
        MissingMatcher res = findAllStudentMissingByCourse(studentRef, courseName, condition);
        List<Missing> missing = res.getMissingList();
        if(month == null && year == null) {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== LocalDateTime.now().getMonthValue()
                                    && mis.getCommencement().getYear() == LocalDateTime.now().getYear()
                    ).toList();
            res.setMissingList(missing);

        } else if (month == null) {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== LocalDateTime.now().getMonthValue()
                                    && mis.getCommencement().getYear() == year
                    ).toList();
            res.setMissingList(missing);
        } else if (year == null) {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== month
                                    && mis.getCommencement().getYear() == LocalDateTime.now().getYear()
                    ).toList();
            res.setMissingList(missing);
        } else {
            missing = missing
                    .stream().filter(
                            mis -> mis.getCommencement().getMonthValue()== month
                                    && mis.getCommencement().getYear() == year
                    ).toList();
        }
        res.setMissingList(missing);
        return res;
    }
}
