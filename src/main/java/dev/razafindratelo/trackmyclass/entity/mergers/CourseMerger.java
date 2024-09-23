package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import org.springframework.stereotype.Component;

@Component
public final class CourseMerger implements GenericMerger<Course, Course> {
    @Override
    public Course mergeFields(Course source, Course target) throws NoSuchFieldException, IllegalAccessException {
        if(source == null || target == null) {
            throw new IllegalArgumentException("Cannot merge null objects");
        }

        if (source.getName() != null)
            target.setName(source.getName());

        if (source.getCredit() > 0)
            target.setCredit(source.getCredit());

        return target;
    }
}
