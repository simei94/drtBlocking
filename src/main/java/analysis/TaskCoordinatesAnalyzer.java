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

    private final List<Double> eventTime = new ArrayList<>();
    private final List<String> eventTypes = new ArrayList<>();
    private final List<Id> DRTVehicles = new ArrayList<>();
    private final List<Id> LinkIds = new ArrayList<>();
    private final List<String> activityTypes = new ArrayList<>();
    private final List<Task.TaskType> taskTypes = new ArrayList<>();

    @Override
    public void handleEvent(ActivityStartEvent event) {

        eventTime.add(event.getTime());
        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(event.getActType());
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(ActivityEndEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(event.getActType());
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(TaskStartedEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(event.getTaskType());

    }

    @Override
    public void handleEvent(TaskEndedEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(event.getTaskType());

    }

    @Override
    public void handleEvent(PassengerRequestScheduledEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(PersonDepartureEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getPersonId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(PersonEntersVehicleEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(PersonLeavesVehicleEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(null);
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(VehicleEntersTrafficEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(VehicleLeavesTrafficEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(LinkLeaveEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    @Override
    public void handleEvent(LinkEnterEvent event) {

        eventTime.add(event.getTime());
        eventTypes.add(event.getEventType());
        DRTVehicles.add(event.getVehicleId());
        LinkIds.add(event.getLinkId());
        activityTypes.add(null);
        taskTypes.add(null);

    }

    public void write() {

        System.out.println(eventTime.size());
        System.out.println(eventTypes.size());
        System.out.println(DRTVehicles.size());
        System.out.println(LinkIds.size());
        System.out.println(activityTypes.size());
        System.out.println(taskTypes.size());

        String delimiter = ";";



        try (PrintWriter writer = new PrintWriter(new File(outputCSVFilePath))){

            StringBuilder sb = new StringBuilder();


            //append column names to csv file
            //sb.append("eventType");
            //sb.append(delimiter);
            //sb.append("vehicleId");
            //sb.append(delimiter);
            //sb.append("linkId");
            //sb.append(delimiter);
            //sb.append("actType");
            //sb.append(delimiter);
            //sb.append("taskType");
            //sb.append(delimiter);
            //sb.append(System.lineSeparator());

            //writer.write(sb.toString());

            //append types and ids from lists to csv file
            while(eventTypes.size() > 0) {

                sb.append(eventTime.remove(0));
                sb.append(delimiter);
                sb.append(eventTypes.remove(0));
                sb.append(delimiter);
                sb.append(DRTVehicles.remove(0));
                sb.append(delimiter);
                sb.append(LinkIds.remove(0));
                sb.append(delimiter);
                sb.append(activityTypes.remove(0));
                sb.append(delimiter);
                sb.append(taskTypes.remove(0));
                sb.append(delimiter);
                sb.append(System.lineSeparator());

                writer.write(sb.toString());

            }

            System.out.println(eventTime.size());
            System.out.println(eventTypes.size());
            System.out.println(DRTVehicles.size());
            System.out.println(LinkIds.size());
            System.out.println(activityTypes.size());
            System.out.println(taskTypes.size());
            System.out.println(outputCSVFilePath + " written!");


        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }
}
