package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import org.springframework.stereotype.Component;

@Component
public final class TeacherMerger implements GenericMerger<Teacher, Teacher> {

    @Override
    public Teacher mergeFields(Teacher source, Teacher target) {
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

        if (!target.isAssistant() && source.isAssistant()) {
            target.setAssistant(true);
        }
        return target;
    }
}
