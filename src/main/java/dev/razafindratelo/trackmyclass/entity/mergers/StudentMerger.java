package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.users.Student;
import dev.razafindratelo.trackmyclass.services.groupAndLevelServices.GroupAndLevelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public final class StudentMerger implements GenericMerger<Student, Student>{
    private final GroupAndLevelService groupAndLevelService;

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

        if (
                source.getGroup()!= null
                && source.getLevel() != null
                && groupAndLevelService.checkLevelAndGroupMap(source.getGroup(), source.getLevel())
        )
            target.setGroup(source.getGroup());

        if (
                source.getLevel() != null
                && source.getGroup() == null
                && groupAndLevelService.checkLevelAndGroupMap(target.getGroup(), source.getLevel())
        )
            target.setLevel(source.getLevel());
        if (
                source.getGroup() != null
                && source.getLevel() == null
                && groupAndLevelService.checkLevelAndGroupMap(source.getGroup(), target.getLevel())
        )
            target.setGroup(source.getGroup());

        return target;
    }
}
