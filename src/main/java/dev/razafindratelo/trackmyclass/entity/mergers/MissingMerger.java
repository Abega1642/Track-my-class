package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.attendances.Missing;
import org.springframework.stereotype.Component;

@Component
public final class MissingMerger implements GenericMerger<Missing, Missing> {
    @Override
    public Missing mergeFields(Missing source, Missing target) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }
}
