package events;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.GenericEvent;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.vehicles.Vehicle;

import java.util.Map;
import java.util.Objects;

public class DrtBlockingEndedEvent1 extends Event {

    public static final String EVENT_TYPE = "DrtBlocking ended";
    public static final String ATTRIBUTE_VEHICLE_ID = "vehicleId";
    private final Id<DvrpVehicle> vehicleId;

    public DrtBlockingEndedEvent1(double timeOfDay, Id<DvrpVehicle> vehicleId) {
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

    public static DrtBlockingEndedEvent1 convert(GenericEvent event) {
        Map<String, String> attributes = event.getAttributes();
        double time = Double.parseDouble(attributes.get(ATTRIBUTE_TIME));
//        String type = Objects.requireNonNull(attributes.get(ATTRIBUTE_TYPE));

        Id<DvrpVehicle> vehicleId = Id.create(attributes.get(ATTRIBUTE_VEHICLE_ID), DvrpVehicle.class);

        //I'd like to save the x and y coords as coord but dont know how to convert them from string to coord, HOW?
//        String yCoord = Objects.requireNonNull(attributes.get(ATTRIBUTE_Y));
//        String xCoord = Objects.requireNonNull(attributes.get(ATTRIBUTE_X));
//        return new DrtBlockingEndedEvent1(time, type, vehicleId, yCoord, xCoord);
        return new DrtBlockingEndedEvent1(time, vehicleId);

    }
}
