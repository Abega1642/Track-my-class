package dev.razafindratelo.trackmyclass.services.teacherServices;

import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    Teacher findTeacherById(String teacherRef);

    List<Teacher> findAllTeachers();

    Teacher updateTeacher(String teacherRef, Teacher teacher);

    Teacher partialTeacherUpdate(String teacherRef, Teacher teacher) throws NoSuchFieldException, IllegalAccessException;
}
