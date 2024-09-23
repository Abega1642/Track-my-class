package dev.razafindratelo.trackmyclass.services.corServices;

import dev.razafindratelo.trackmyclass.dao.CorDAO;
import dev.razafindratelo.trackmyclass.dto.CorDTO;
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
        return corDAO.getAllCors();
    }

    @Override
    public String corRefGenerator() {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear()).substring(2);

        if (findAll().isEmpty()) {
            System.out.println("Emptyone");
            return "COR"+year+"001";
        } else {
            List<String> corRefs = findAll()
                    .stream()
                    .map(Cor::getCorRef)
                    .sorted()
                    .toList();

            if (corRefs.isEmpty()) {
                return "COR"+year+"001";
            } else {
                String corRef = corRefs.getLast();
                int number = Integer.parseInt(corRef.substring(3, 8)) + 1;
                return corRef.substring(0, 3) + number;
            }
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
    public List<Cor> addCors(List<CorDTO> cors) {
        List<Cor> result = new ArrayList<>();
        for(CorDTO cor: cors) {
            Student student = studentService.findStudentById(cor.getStd());
            Cor cr = new Cor(corRefGenerator(), student);
            System.out.println(cr);
            Cor addedCor = corDAO.addCor(cr);
            result.add(addedCor);
        }
        return result;
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

                MissingMatcher ms = missingService.findStudentMissingByCourse(std, courseName, month, year, "n");
                if (!ms.getAttendances().isEmpty())
                    missingByCourse.add(ms);
            }
        }
        List<String> filteredSTDs = missingByCourse.stream().map(missing -> missing.getStudent().getUserRef()).toList();

        List<String> STDsRelatedToCOR = allSTDsToCOR(filteredSTDs, missingByCourse);

        List<CorDTO> cors = new ArrayList<>();

        for(String std: STDsRelatedToCOR) {
            cors.add(new CorDTO(null, std));
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
