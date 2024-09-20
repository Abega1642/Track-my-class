package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import org.springframework.stereotype.Component;

@Component
public final class StudentMerger implements GenericMerger<Student, Student> {
    @Override
    public Student mergeFields(Student source, Student target) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }
}
