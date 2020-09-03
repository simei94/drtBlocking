package events;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.GenericEvent;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.optimizer.Request;
import org.matsim.contrib.dvrp.passenger.DvrpPassengerEventsReader;
import org.matsim.contrib.dvrp.schedule.Task;
import org.matsim.contrib.dvrp.vrpagent.TaskEndedEvent;
import org.matsim.contrib.dvrp.vrpagent.TaskStartedEvent;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.utils.io.MatsimXmlParser;
import org.matsim.drtBlockings.DrtBlockingRequest;
import org.matsim.drtBlockings.events.DrtBlockingRequestRejectedEvent;
import org.matsim.drtBlockings.events.DrtBlockingRequestScheduledEvent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.matsim.contrib.dvrp.vrpagent.AbstractTaskEvent.ATTRIBUTE_TASK_INDEX;
import static org.matsim.contrib.dvrp.vrpagent.AbstractTaskEvent.ATTRIBUTE_TASK_TYPE;
import static org.matsim.drtBlockings.events.DrtBlockingEndedEvent.*;
//import static events.DrtBlockingEndedEvent.*;
//import static events.DrtBlockingEndedEvent.EVENT_TYPE;
import static org.matsim.drtBlockings.events.DrtBlockingRequestRejectedEvent.ATTRIBUTE_SUBMISSION_TIME;
import static org.matsim.contrib.dvrp.vrpagent.TaskStartedEvent.ATTRIBUTE_DVRP_MODE;
import static org.matsim.contrib.dvrp.vrpagent.TaskStartedEvent.ATTRIBUTE_DVRP_VEHICLE;


public final class DrtBlockingEventsReader extends MatsimXmlParser {

    private DvrpPassengerEventsReader delegate;

    public DrtBlockingEventsReader(EventsManager events) {
        delegate = new DvrpPassengerEventsReader(events);
        this.setValidating(false);
        delegate.addCustomEventMapper(EVENT_TYPE, this::drtBlockingEndedEvent);
        delegate.addCustomEventMapper(EVENT_TYPE, this::drtBlockingRequestRejectedEvent);
        delegate.addCustomEventMapper(EVENT_TYPE, this::drtBlockingRequestScheduledEvent);
        delegate.addCustomEventMapper(EVENT_TYPE, this::taskStartedEvent);
        delegate.addCustomEventMapper(EVENT_TYPE, this::taskEndedEvent);
    }

    private DrtBlockingEndedEvent drtBlockingEndedEvent(GenericEvent event) {

        Map<String, String> attributes = event.getAttributes();

        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
//        String type = attributes.get(ATTRIBUTE_TYPE);
//        String xCoord = attributes.get(ATTRIBUTE_X);
//        String yCoord = attributes.get(ATTRIBUTE_Y);

        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_VEHICLE_ID), DvrpVehicle.class);

        // it seems like you only have to return the attributes which are defined in the event class
        return new DrtBlockingEndedEvent(time, vehicleId);
    }

    private DrtBlockingRequestRejectedEvent drtBlockingRequestRejectedEvent(GenericEvent event) {
        Map<String, String> attributes = event.getAttributes();

        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
        Id<Request> requestId = Id.create(attributes.get(DrtBlockingRequestRejectedEvent.ATTRIBUTE_REQUEST_ID), Request.class);
        double submissionTime = Double.parseDouble(attributes.get(ATTRIBUTE_SUBMISSION_TIME));

        return new DrtBlockingRequestRejectedEvent(time, requestId, submissionTime);
    }

    private DrtBlockingRequestScheduledEvent drtBlockingRequestScheduledEvent(GenericEvent event) {
        Map<String, String> attributes = event.getAttributes();

        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
        Id<Request> requestId = Id.create(attributes.get(DrtBlockingRequestScheduledEvent.ATTRIBUTE_REQUEST), Request.class);
        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_VEHICLE_ID), DvrpVehicle.class);

        return new DrtBlockingRequestScheduledEvent(time, requestId, vehicleId);
    }

    private TaskStartedEvent taskStartedEvent (GenericEvent event, Task task) {
        Map<String, String> attributes = event.getAttributes();
        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
        String dvrpMode = attributes.get(ATTRIBUTE_DVRP_MODE);
        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_DVRP_VEHICLE), DvrpVehicle.class);

        Task.TaskType taskType = task.getTaskType();

//        Task.TaskType taskType = attributes.get(TaskStartedEvent.ATTRIBUTE_TASK_TYPE);
        int taskIndex = Integer.parseInt(attributes.get(ATTRIBUTE_TASK_INDEX));
        Id<Link> linkId = Id.createLinkId(attributes.get(TaskStartedEvent.ATTRIBUTE_LINK));

        return new TaskStartedEvent(time, dvrpMode, vehicleId, taskType, taskIndex, linkId);
    }

    private TaskEndedEvent taskEndedEvent (GenericEvent event, Task task) {
        Map<String, String> attributes = event.getAttributes();
        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
        String dvrpMode = attributes.get(ATTRIBUTE_DVRP_MODE);
        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_DVRP_VEHICLE), DvrpVehicle.class);

        Task.TaskType taskType = task.getTaskType();

//        Task.TaskType taskType = attributes.get(TaskStartedEvent.ATTRIBUTE_TASK_TYPE);
        int taskIndex = Integer.parseInt(attributes.get(ATTRIBUTE_TASK_INDEX));
        Id<Link> linkId = Id.createLinkId(attributes.get(TaskStartedEvent.ATTRIBUTE_LINK));

        return new TaskEndedEvent(time, dvrpMode, vehicleId, taskType, taskIndex, linkId);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        delegate.characters(ch, start, length);
    }

    @Override
    public void startTag(String name, Attributes atts, Stack<String> context) {
        delegate.startTag(name, atts, context);
    }

    @Override
    public void endTag(String name, String content, Stack<String> context) {
        delegate.endTag(name, content, context);
    }
}
