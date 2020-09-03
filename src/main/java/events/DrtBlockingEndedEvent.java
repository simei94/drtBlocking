package events;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.Event;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;

import java.util.Map;

public class DrtBlockingEndedEvent extends Event {

    public static final String EVENT_TYPE = "DrtBlocking ended";
    public static final String ATTRIBUTE_VEHICLE_ID = "vehicleId";
    private final Id<DvrpVehicle> vehicleId;

    public DrtBlockingEndedEvent(double timeOfDay, Id<DvrpVehicle> vehicleId) {
        super(timeOfDay);
        this.vehicleId = vehicleId;
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> attr = super.getAttributes();
        attr.put(ATTRIBUTE_VEHICLE_ID, vehicleId + "");
        return attr;
    }

    public Id<DvrpVehicle> getVehicleId() {
        return vehicleId;
    }
}