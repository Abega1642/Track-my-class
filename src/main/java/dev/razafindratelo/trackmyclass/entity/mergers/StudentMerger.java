package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import org.springframework.stereotype.Component;

@Component
public final class StudentMerger implements GenericMerger<Student, Student> {

    @Override
    public Student mergeFields(Student source, Student target) throws NoSuchFieldException, IllegalAccessException {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Teacher must not be null");
        }

        if(source.getLastName() != null) {
            target.setLastName(source.getLastName());
        } else if (source.getFirstName() != null) {
            target.setFirstName(source.getFirstName());
        }  else if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        } else if (source.getPhoneNumber() != null) {
            target.setPhoneNumber(source.getPhoneNumber());
        } else if (source.getLevel() != null) {
            target.setLevel(source.getLevel());
        } else if (source.getGroup()!= null) {
            target.setGroup(source.getGroup());
        }

        return target;
    }
}
