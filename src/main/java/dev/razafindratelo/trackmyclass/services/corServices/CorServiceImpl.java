package dev.razafindratelo.trackmyclass.services.corServices;

import dev.razafindratelo.trackmyclass.dao.CorDAO;
import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import dev.razafindratelo.trackmyclass.entity.course.Course;
import dev.razafindratelo.trackmyclass.entity.matchers.MissingMatcher;
import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceDuplicatedException;
import dev.razafindratelo.trackmyclass.services.courseServices.CourseService;
import dev.razafindratelo.trackmyclass.services.missingServices.MissingService;
import dev.razafindratelo.trackmyclass.services.studentServices.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CorServiceImpl implements CorService {
    private final StudentService studentService;
    private CorDAO corDAO;
    private MissingService missingService;
    private CourseService courseService;

    @Override
    public List<Cor> findAll() {
        updateCorList();
        return corDAO.getAllCors();
    }

    @Override
    public String corRefGenerator() {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear()).substring(2);

        if (findAll().isEmpty()) {
            return "COR"+year+"001";
        } else {
            String corRefs = findAll()
                    .stream()
                    .map(cor -> cor.getStudent().getUserRef())
                    .sorted()
                    .toList()
                    .getLast();

            int number = Integer.parseInt(corRefs.substring(3, 8)) + 1;

            return corRefs.substring(0, 3) + number;
        }
    }

    @Override
    public Cor findCorByItsRef(String corRef) {
        if(corRef == null || corRef.isEmpty())
            throw new IllegalRequestException("COR ref must not be null or empty");

        return findAll()
                .stream()
                .filter(cor -> cor.getCorRef().equalsIgnoreCase(corRef))
                .toList()
                .getFirst();
    }

    @Override
    public List<Cor> findByStudentRef(String studentRef) {
        return findAll()
                .stream()
                .filter(cor -> cor.getStudent().getUserRef().equalsIgnoreCase(studentRef))
                .toList();
    }

    @Override
    public Cor addCor(Cor cor) {
        if (cor.getStudent() == null
               || cor.getStudent().getUserRef() == null
               || cor.getStudent().getUserRef().isEmpty()
        ) {

            throw new IllegalRequestException("COR student must not be null or empty");

        } else if (cor.getCorRef() == null
                || cor.getCorRef().length() != 8
                || !cor.getCorRef().substring(0, 3).equalsIgnoreCase("COR")
        ) {
            cor.setCorRef(corRefGenerator());

        } else if (findCorByItsRef(cor.getCorRef()) != null) {

            throw new ResourceDuplicatedException("Cor already exists");

        }
        return corDAO.addCor(cor);
    }

    @Override
    public List<Cor> addCors(List<Cor> cors) {
        for(Cor cor: cors) {
            addCor(cor);
        }
        return cors;
    }

    @Override
    public Cor deleteCor(String corRef) {
        Cor cor = findCorByItsRef(corRef);

        if(cor == null)
            return new Cor(corRef, null);

        return corDAO.deleteCor(cor);
    }

    @Override
    public List<Cor> updateCorList() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        List<Course> courses = courseService.getAllCourses();
        List<String> STDs = studentService.findAllStudentRef();
        List<MissingMatcher> missingByCourse = new ArrayList<>();

        for(Course crs : courses) {
            String courseName = crs.getName();
            for (String std: STDs) {
                missingByCourse.add(missingService.findStudentMissingByCourse(std, courseName, month, year, "n"));
            }
        }

        List<String> STDsRelatedToCOR = allSTDsToCOR(STDs, missingByCourse);
        List<Cor> cors = new ArrayList<>();
        for(String std: STDsRelatedToCOR) {
            Student student = studentService.findStudentById(std);
            cors.add(new Cor(
                    corRefGenerator(),
                    student
            ));
        }
        return addCors(cors);
    }

    private List<String> allSTDsToCOR(List<String> STDs, List<MissingMatcher> missingList) {
        List<String> result = new ArrayList<>();

        for(String std : STDs) {
            int frequency = missingList
                    .stream()
                    .filter(missing -> missing.getStudent().getUserRef().equalsIgnoreCase(std))
                    .toList()
                    .size();
            if (frequency >= 3)
                result.add(std);
        }
        return result;
    }
}
