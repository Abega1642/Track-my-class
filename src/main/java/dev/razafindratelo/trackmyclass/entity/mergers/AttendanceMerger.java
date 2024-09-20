package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.attendances.Attendance;
import org.springframework.stereotype.Component;

@Component
public final class AttendanceMerger implements GenericMerger<Attendance, Attendance> {

    @Override
    public Attendance mergeFields(Attendance source, Attendance target) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }
}
