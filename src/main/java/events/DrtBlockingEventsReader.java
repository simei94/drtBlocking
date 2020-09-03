package events;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.GenericEvent;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.optimizer.Request;
import org.matsim.contrib.dvrp.passenger.DvrpPassengerEventsReader;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.utils.io.MatsimXmlParser;
import org.matsim.drtBlockings.DrtBlockingRequest;
import org.matsim.drtBlockings.events.DrtBlockingEndedEvent;
import org.matsim.drtBlockings.events.DrtBlockingRequestRejectedEvent;
import org.matsim.drtBlockings.events.DrtBlockingRequestScheduledEvent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static events.DrtBlockingEndedEvent.*;

public final class DrtBlockingEventsReader extends MatsimXmlParser {

    private DvrpPassengerEventsReader delegate;

    public DrtBlockingEventsReader(EventsManager events) {
        delegate = new DvrpPassengerEventsReader(events);
        this.setValidating(false);
        delegate.addCustomEventMapper(EVENT_TYPE, this::drtBlockingEndedEvent);
        delegate.addCustomEventMapper(EVENT_TYPE, this::drtBlockingRequestRejectedEvent);
        delegate.addCustomEventMapper(EVENT_TYPE, this::drtBlockingRequestScheduledEvent);
    }

    private DrtBlockingEndedEvent drtBlockingEndedEvent(GenericEvent event) {

        Map<String, String> attributes = event.getAttributes();

        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
//        String type = attributes.get(ATTRIBUTE_TYPE);
//        String xCoord = attributes.get(ATTRIBUTE_X);
//        String yCoord = attributes.get(ATTRIBUTE_Y);
        String vehicle = attributes.get(ATTRIBUTE_VEHICLE);

//        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_VEHICLE), DvrpVehicle.class);

        // it seems like you only have to return the attributes which are defined in the event class
        return new DrtBlockingEndedEvent(time, vehicle);
    }

    private DrtBlockingRequestRejectedEvent drtBlockingRequestRejectedEvent(GenericEvent event) {
        Map<String, String> attributes = event.getAttributes();
        Map<Double, DrtBlockingRequest> test = new HashMap<>();

        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));

        test.put(tim);
        DrtBlockingRequest request = attributes.get(DrtBlockingRequestRejectedEvent.ATTRIBUTE_REQUEST);

        return new DrtBlockingRequestRejectedEvent(time, request);
    }

    private DrtBlockingRequestScheduledEvent drtBlockingRequestScheduledEvent(GenericEvent event) {
        Map<String, String> attributes = event.getAttributes();

        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
        Id<Request> requestId = Id.create(attributes.get(DrtBlockingRequestScheduledEvent.ATTRIBUTE_REQUEST), Request.class);
        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_VEHICLE), DvrpVehicle.class);

        return new DrtBlockingRequestScheduledEvent(time, requestId, vehicleId);
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
