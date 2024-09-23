package dev.razafindratelo.trackmyclass.services.teacherServices;

import dev.razafindratelo.trackmyclass.dao.TeacherDAO;
import dev.razafindratelo.trackmyclass.entity.mergers.TeacherMerger;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.entity.users.User;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceDuplicatedException;
import dev.razafindratelo.trackmyclass.exceptionHandler.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private TeacherDAO teacherDAO;
    private TeacherMerger teacherMerger;

    @Override
    public Teacher findTeacherById(String teacherRef) {
        if (teacherRef == null || teacherRef.isEmpty()) {
            throw new IllegalRequestException("Teacher ref must not be null or empty");
        }
        Teacher teacher = teacherDAO.findTeacherById(teacherRef.toUpperCase());
        if(teacher == null) {
            throw new ResourceNotFoundException("Teacher not found with id " + teacherRef);
        }
        return teacher;
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return teacherDAO.findAllTeachers();
    }

    @Override
    public List<String> findAllTeachersRef() {
        return findAllTeachers().stream().map(User::getUserRef).toList();
    }

    @Override
    public String teacherRefGenerator() {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear()).substring(2);

        if (findAllTeachersRef().isEmpty()) {
            return "TCH"+year+"001";
        }
        String corRefs = findAllTeachersRef()
                .stream()
                .sorted()
                .toList()
                .getLast();

        int number = Integer.parseInt(corRefs.substring(3, 8)) + 1;

        if (!corRefs.substring(3, 5).equals(year)) {
            return "TCH"+year+"001";
        }

        return corRefs.substring(0, 3) + number;
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        if(teacher == null)
            throw new IllegalRequestException("Teacher must not be null");

        if (findAllTeachersRef().contains(teacher.getUserRef()))
            throw new ResourceDuplicatedException("Teacher with id :" + teacher.getUserRef() + " already exists");

        if (teacher.getEmail() == null || teacher.getLastName() == null || teacher.getPhoneNumber() == null)
            throw new IllegalRequestException("Teachers attributes must not be null except the firstname");

        if (teacher.getUserRef() == null || teacher.getUserRef().length() != 8
                || !teacher.getUserRef().substring(0,3).equalsIgnoreCase("TCH")) {

            teacher.setUserRef(teacherRefGenerator());
        }

        teacher.setUserRef(teacher.getUserRef().toUpperCase());
        return teacherDAO.addTeacher(teacher);
    }

    @Override
    public Teacher updateTeacher(String teacherRef, Teacher teacher) {
        if (teacher == null || teacherRef == null || teacherRef.isEmpty()) {
            throw new IllegalRequestException("Teacher must not be null");
        } else if (
                teacher.getUserRef() == null
               || teacher.getEmail() == null
               || teacher.getLastName() == null
               || teacher.getPhoneNumber() == null
        ) {
            throw new IllegalRequestException("Teachers attributes must not be null except the firstname");
        }
        Teacher updatedTeacher = teacherDAO.integralUpdateTeacher(teacherRef.toUpperCase(), teacher);

        if(updatedTeacher == null) {
            throw new InternalException("Teacher updated failed");
        }
        return updatedTeacher;
    }

    @Override
    public Teacher partialTeacherUpdate(String teacherRef, Teacher teacher) {
        if (teacher == null || teacherRef == null || teacherRef.isEmpty()) {
            throw new IllegalRequestException("Teacher must not be null");
        }
        Teacher oldTeacher = findTeacherById(teacherRef);
        oldTeacher = teacherMerger.mergeFields(teacher, oldTeacher);
        System.out.println(oldTeacher);
        return updateTeacher(teacherRef, oldTeacher);

    }

    @Override
    public Teacher deleteTeacher(String teacherRef) {
        if(teacherRef == null) {
            throw new IllegalRequestException("Teacher ref must not be null");
        }
        Teacher teacher = teacherDAO.findTeacherById(teacherRef);
        if(teacher == null) {
            new Teacher(
                    "-",
                    "NO_MATCH",
                    "NO_MATCH",
                    "NO_MATCH",
                    "NO_MATCH",
                    true
            );
        }
        return teacherDAO.deleteTeacher(teacherRef.toUpperCase());
    }

}
