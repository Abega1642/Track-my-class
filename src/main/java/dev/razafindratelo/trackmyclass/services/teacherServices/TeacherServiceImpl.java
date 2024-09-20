package dev.razafindratelo.trackmyclass.services.teacherServices;

import dev.razafindratelo.trackmyclass.dao.TeacherDAO;
import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import dev.razafindratelo.trackmyclass.exceptionHandler.IllegalRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private TeacherDAO teacherDAO;

    @Override
    public Teacher findTeacherById(String teacherRef) {
        if (teacherRef == null || teacherRef.isEmpty()) {
            throw new IllegalRequestException("Teacher ref must not be null or empty");
        } else {
            return this.teacherDAO.findTeacherById(teacherRef);
        }
    }
}
