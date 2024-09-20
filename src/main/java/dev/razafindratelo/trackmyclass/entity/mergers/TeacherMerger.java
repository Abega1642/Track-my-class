package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.users.Teacher;
import org.springframework.stereotype.Component;

@Component
public final class TeacherMerger implements GenericMerger<Teacher, Teacher> {
    
    @Override
    public Teacher mergeFields(Teacher source, Teacher target) throws NoSuchFieldException, IllegalAccessException {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Teacher must not be null");
        }

        if(source.getLastName() != null) {
            target.setLastName(source.getLastName());
        } else if (source.getFirstName() != null) {
            target.setFirstName(source.getFirstName());
        } else if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        } else if (source.getPhoneNumber() != null) {
            target.setPhoneNumber(source.getPhoneNumber());
        } else if (!target.isAssistant() && source.isAssistant()) {
            target.setAssistant(true);
        }
        return target;
    }
}
