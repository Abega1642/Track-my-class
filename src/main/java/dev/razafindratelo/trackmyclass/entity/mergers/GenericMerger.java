package dev.razafindratelo.trackmyclass.entity.mergers;

import org.springframework.stereotype.Component;

@Component
public sealed interface GenericMerger<T, G>
    permits
        TeacherMerger,
        StudentMerger,
        CourseMerger,
        AttendanceMerger,
        DelayMerger,
        MissingMerger
{

    T mergeFields(T source, G target) throws NoSuchFieldException, IllegalAccessException;
}
