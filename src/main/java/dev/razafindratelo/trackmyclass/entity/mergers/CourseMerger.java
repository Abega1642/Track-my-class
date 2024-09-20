package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.course.Course;
import org.springframework.stereotype.Component;

@Component
public final class CourseMerger implements GenericMerger<Course, Course> {
    @Override
    public Course mergeFields(Course source, Course target) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }
}
