package dev.razafindratelo.trackmyclass.entity.mergers;

import org.springframework.stereotype.Component;

@Component
public sealed interface GenericMerger<T, G>
        permits AttendanceMerger, CourseMerger, DelayMerger, MissingMerger, StudentMerger, TeacherMerger
{
    T mergeFields(T source, G target) throws NoSuchFieldException, IllegalAccessException;
}
