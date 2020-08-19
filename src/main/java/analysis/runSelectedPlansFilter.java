package analysis;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class runSelectedPlansFilter {

    public static void main(String[] args) {

        //HOW CAN I LOAD PLANS FILE WITHOUT CONFIG??


        String workingFolder = "C:/Users/simon/tubCloud/Shared/MA-Meinhardt/berlinv5.5-1pct/baseCase1pct/";

        //load plans file
        //Config config = ConfigUtils.loadConfig(workingFolder + "drtBlockingBase1pct.output_config_reduced.xml");
        System.out.println("hshshf");
        Config config = ConfigUtils.createConfig();
        config.plans().setInputFile(workingFolder + "drtBlockingBase1pct.output_plans.xml.gz");
        config.network().setInputFile(workingFolder + "drtBlockingBase1pct.output_network.xml.gz");

        //load scenario
        Scenario scenario = ScenarioUtils.loadScenario(config);

        //SelectedPlansFilter
        SelectedPlansFilter selectedPlansFilter = new SelectedPlansFilter(scenario, workingFolder + "test_plans.xml.gz");
        selectedPlansFilter.runFilter();

    }
}
