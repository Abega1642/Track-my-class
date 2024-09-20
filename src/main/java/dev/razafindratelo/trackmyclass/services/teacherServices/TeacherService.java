package dev.razafindratelo.trackmyclass.services.teacherServices;

import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import org.springframework.stereotype.Service;

@Service
public interface TeacherService {
    Teacher findTeacherById(String teacherRef);
}
