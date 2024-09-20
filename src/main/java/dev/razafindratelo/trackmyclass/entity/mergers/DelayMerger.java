package dev.razafindratelo.trackmyclass.entity.mergers;

import dev.razafindratelo.trackmyclass.entity.attendances.Delay;
import org.springframework.stereotype.Component;

@Component
public final class DelayMerger implements GenericMerger<Delay, Delay> {
    @Override
    public Delay mergeFields(Delay source, Delay target) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }
}
