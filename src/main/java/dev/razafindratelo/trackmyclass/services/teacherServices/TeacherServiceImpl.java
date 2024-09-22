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
        Teacher teacher = teacherDAO.findTeacherById(teacherRef);
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
    public Teacher addTeacher(Teacher teacher) {
        if(teacher == null) {
            throw new IllegalRequestException("Teacher must not be null");
        } else if (findAllTeachersRef().contains(teacher.getUserRef())) {
            throw new ResourceDuplicatedException("Teacher with id :" + teacher.getUserRef() + " already exists");
        }
        return teacherDAO.addTeacher(teacher);
    }

    @Override
    public Teacher updateTeacher(String teacherRef, Teacher teacher) {
        if (teacher == null || teacherRef == null || teacherRef.isEmpty()) {
            throw new IllegalRequestException("Teacher must not be null");
        }
        Teacher updatedTeacher = teacherDAO.integralUpdateTeacher(teacherRef, teacher);
        if(updatedTeacher == null) {
            throw new InternalException("Teacher updated failed");
        }
        return updatedTeacher;
    }

    @Override
    public Teacher partialTeacherUpdate(String teacherRef, Teacher teacher)
            throws NoSuchFieldException, IllegalAccessException {
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
        return Objects.requireNonNullElseGet(teacher, () -> new Teacher(
                "-",
                "NO_MATCH",
                "NO_MATCH",
                "NO_MATCH",
                "NO_MATCH",
                true
        ));
    }

}
