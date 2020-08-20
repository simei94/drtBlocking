package analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.*;
import org.matsim.api.core.v01.events.handler.*;
import org.matsim.contrib.drt.schedule.DrtTaskType;
import org.matsim.contrib.dvrp.passenger.PassengerRequest;
import org.matsim.contrib.dvrp.passenger.PassengerRequestScheduledEvent;
import org.matsim.contrib.dvrp.passenger.PassengerRequestScheduledEventHandler;
import org.matsim.contrib.dvrp.schedule.Task;
import org.matsim.contrib.dvrp.vrpagent.TaskEndedEvent;
import org.matsim.contrib.dvrp.vrpagent.TaskEndedEventHandler;
import org.matsim.contrib.dvrp.vrpagent.TaskStartedEvent;
import org.matsim.contrib.dvrp.vrpagent.TaskStartedEventHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskCoordinatesAnalyzer implements ActivityStartEventHandler, ActivityEndEventHandler, TaskStartedEventHandler,
        TaskEndedEventHandler, PassengerRequestScheduledEventHandler, PersonDepartureEventHandler, PersonArrivalEventHandler,
        PersonEntersVehicleEventHandler, PersonLeavesVehicleEventHandler, VehicleEntersTrafficEventHandler, VehicleLeavesTrafficEventHandler,
        LinkLeaveEventHandler, LinkEnterEventHandler {

    String outputCSVFilePath = "C:/Users/simon/tubCloud/MA/TaskCoordinates/DRTTaskCoordinates.csv";

    private final List<String> eventTypes = new ArrayList<>();
    private final List<Id> DRTVehicles = new ArrayList<>();
    private final List<Id> LinkIds = new ArrayList<>();
    private final List<String> activityTypes = new ArrayList<>();
    private final List<Task.TaskType> taskTypes = new ArrayList<>();


    public void handleEvent(ActivityStartEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(event.getActType());
        taskTypes.add(null);

    }

    public void handleEvent(ActivityEndEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(event.getActType());
        taskTypes.add(null);

    }

    public void handleEvent(TaskStartedEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(event.getTaskType());

    }

    public void handleEvent(TaskEndedEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(event.getTaskType());

    }

    public void handleEvent(PassengerRequestScheduledEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(PersonDepartureEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(PersonArrivalEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(PersonEntersVehicleEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(PersonLeavesVehicleEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(VehicleEntersTrafficEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(VehicleLeavesTrafficEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(LinkLeaveEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void handleEvent(LinkEnterEvent event) {

        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void write() {

        System.out.println(eventTypes.size());
        System.out.println(DRTVehicles.size());
        System.out.println(LinkIds.size());
        System.out.println(activityTypes.size());
        System.out.println(taskTypes.size());

        String fileName = "";
        String delimiter = ";";

        try (PrintWriter writer = new PrintWriter(new File(outputCSVFilePath))){

            StringBuilder sb = new StringBuilder();
            sb.append(eventTypes);
            sb.append(delimiter);
            sb.append(DRTVehicles);
            sb.append(delimiter);
            sb.append(LinkIds);
            sb.append(delimiter);
            sb.append(activityTypes);
            sb.append(delimiter);
            sb.append(taskTypes);
            sb.append(delimiter);

            writer.write(sb.toString());

            System.out.println(outputCSVFilePath + " written!");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
