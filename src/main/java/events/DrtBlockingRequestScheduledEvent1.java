package events;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.GenericEvent;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.optimizer.Request;

import java.util.Map;

public class DrtBlockingRequestScheduledEvent1 extends Event {

    public static final String EVENT_TYPE = "DrtBlockingRequest scheduled";
    public static final String ATTRIBUTE_VEHICLE = "vehicle";
    public static final String ATTRIBUTE_REQUEST = "request";
    private final Id<Request> requestId;

    private final Id<DvrpVehicle> vehicleId;

    public DrtBlockingRequestScheduledEvent1(double timeOfDay, Id<Request> requestId, Id<DvrpVehicle> vehicleId) {
        super(timeOfDay);
        this.requestId = requestId;
        this.vehicleId = vehicleId;
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> attr = super.getAttributes();
        attr.put(ATTRIBUTE_REQUEST, requestId + "");
        attr.put(ATTRIBUTE_VEHICLE, vehicleId + "");
        return attr;
    }

    public Id<Request> getRequestId() {
        return requestId;
    }

    public Id<DvrpVehicle> getVehicleId() {
        return vehicleId;
    }

    public static DrtBlockingRequestScheduledEvent1 convert(GenericEvent event) {
        Map<String, String> attributes = event.getAttributes();
        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
        Id<Request> requestId = Id.create(attributes.get(ATTRIBUTE_REQUEST), Request.class);
        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_VEHICLE), DvrpVehicle.class);

        return new DrtBlockingRequestScheduledEvent1(time, requestId, vehicleId);
    }

}
