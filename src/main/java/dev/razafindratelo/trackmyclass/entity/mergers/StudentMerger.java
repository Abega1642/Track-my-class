package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import org.springframework.stereotype.Component;

@Component
public final class StudentMerger implements GenericMerger<Student, Student>{

    @Override
    public Student mergeFields(Student source, Student target) {
        if(source == null || target == null) {
            throw new IllegalArgumentException("Cannot merge null objects");
        }
        if(source.getLastName() != null)
            target.setLastName(source.getLastName());

        if(source.getFirstName() != null)
            target.setFirstName(source.getFirstName());

        if(source.getEmail() != null)
            target.setEmail(source.getEmail());

        if(source.getPhoneNumber() != null)
            target.setPhoneNumber(source.getPhoneNumber());

        if (source.getGroup()!= null)
            target.setGroup(source.getGroup());

        if (source.getLevel() != null)
            target.setLevel(source.getLevel());

        return target;
    }
}
