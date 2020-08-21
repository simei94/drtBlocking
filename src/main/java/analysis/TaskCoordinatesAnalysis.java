package analysis;

import org.locationtech.jts.geom.Coordinate;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.VehicleLeavesTrafficEvent;
import org.matsim.api.core.v01.network.Network;
//import org.matsim.contrib.drt.passenger.events.DrtPassengerEventsReader;
import org.matsim.contrib.drt.run.MultiModeDrtConfigGroup;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.contrib.freight.carrier.Tour;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsReaderXMLv1;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.mobsim.jdeqsim.Vehicle;
import org.matsim.core.network.NetworkUtils;
//import org.matsim.drtBlockings.events.DrtBlockingEndedEvent;
//import org.matsim.drtBlockings.events.DrtBlockingRequestScheduledEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskCoordinatesAnalysis {

    String outputCSVFilePath = "";

    private Map<Coordinate, Coordinate> EventCoordinates = new HashMap<>();

//    public Id id;
//    public Coordinate x;
//    public Coordinate y;

    public static void main(String[] args) {

        String pathToConfig = "C:/Users/simon/tubCloud/MA/TaskCoordinates/drtBlockingBase1pct.output_config_reduced.xml";
        String pathToEvents = "C:/Users/simon/tubCloud/MA/TaskCoordinates/dummyOutputEvents.xml";
        String outputCSVFilePath = "C:/Users/simon/tubCloud/MA/TaskCoordinates/DRTTaskCoordinates.csv";

        Config config = ConfigUtils.loadConfig(pathToConfig, new MultiModeDrtConfigGroup(), new DvrpConfigGroup());
        Network network = NetworkUtils.readNetwork(config.network().getInputFile());




    }

    public void readEvents(Config config, String pathToEvents) {

        EventsManager eventsManager = EventsUtils.createEventsManager(config);
        TaskCoordinatesAnalyzer analyzer = new TaskCoordinatesAnalyzer();
        eventsManager.addHandler(analyzer);
        EventsReaderXMLv1 basicReader = new EventsReaderXMLv1(eventsManager);
        basicReader.readFile(pathToEvents);

        //DrtPassengerEventsReader reader = new DrtPassengerEventsReader(eventsManager);
        //reader.readFile(pathToEvents);

            //So sehen Events aus:
        	//<event time="0.0" type="actend" person="58883201" link="147740" actType="home_93000.0"  />
            //Also: link holen und aus Link die Koordinaten holen?



//        id = event.getVehicleId();


//        this.EventCoordinates.put(x, y);

    }
}
