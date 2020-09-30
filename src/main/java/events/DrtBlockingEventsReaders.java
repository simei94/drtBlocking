package events;

import org.matsim.contrib.drt.passenger.events.DrtRequestSubmittedEvent;
import org.matsim.contrib.dvrp.passenger.PassengerRequestRejectedEvent;
import org.matsim.contrib.dvrp.schedule.Task;
import org.matsim.contrib.dvrp.vrpagent.TaskEndedEvent;
import org.matsim.contrib.dvrp.vrpagent.TaskStartedEvent;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.drtBlockings.events.DrtBlockingEndedEvent;
import org.matsim.drtBlockings.events.DrtBlockingRequestRejectedEvent;

import java.util.Map;
import java.util.function.Function;

public class DrtBlockingEventsReaders {

    public static Map<String, MatsimEventsReader.CustomEventMapper> createCustomEventMappers(
            Function<String, Task.TaskType> stringToTaskTypeConverter) {
        return Map.of(DrtBlockingEndedEvent1.EVENT_TYPE, DrtBlockingEndedEvent1::convert,//
                DrtBlockingRequestRejectedEvent1.EVENT_TYPE, DrtBlockingRequestRejectedEvent1::convert,//
                DrtBlockingRequestScheduledEvent1.EVENT_TYPE, DrtBlockingRequestScheduledEvent1::convert,//
                //Warum hier andere Syntax? Müsste es nicht auch wie oben funktionieren?
                TaskStartedEvent.EVENT_TYPE, e -> TaskStartedEvent.convert(e, stringToTaskTypeConverter),
                TaskEndedEvent.EVENT_TYPE, e -> TaskEndedEvent.convert(e, stringToTaskTypeConverter)
        );

    }

    public static MatsimEventsReader createEventsReader(EventsManager eventsManager,
                                                        Function<String, Task.TaskType> stringToTaskTypeConverter) {
        MatsimEventsReader reader = new MatsimEventsReader(eventsManager);
        createCustomEventMappers(stringToTaskTypeConverter).forEach(reader::addCustomEventMapper);
        return reader;
    }
}
