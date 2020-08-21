package run;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigReader;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class ActivityDurationPlanFixer {

    String inputFolder = "C:/Users/simon/tubCloud/MA/InputDRT/";
    String outputPlansFile = "drtBlockingBase1pct.output_plansFIXED.xml.gz";
    String configPath = "C:/Users/simon/tubCloud/MA/InputDRT/input_config_reduced.xml";

    public void runFix() {

        //Read config with "damaged" activities and get scenario
        Config config = ConfigUtils.loadConfig(configPath);
        Scenario scenario = ScenarioUtils.loadScenario(config);

        Population population = fixPopulation(configPath, scenario);
        //Population test = ScenarioUtils.createScenario(ConfigUtils.createConfig()).getPopulation();

        //Write new fixed population into file
        PopulationWriter popWriter = new PopulationWriter(population);
        //PopulationWriter popWriter = new PopulationWriter(test);
        popWriter.write(inputFolder + outputPlansFile);
        System.out.println("Fixed output plans file written!");
    }


    public static Population fixPopulation(String configPath, Scenario scenario) {

        //Get pop from scenario
        Population population = scenario.getPopulation();
        //create new empty pop
        //Population fixedPopulation = ScenarioUtils.createScenario(ConfigUtils.createConfig()).getPopulation();


        //search for activitytype car interaction and set max duration
        population.getPersons().values().stream().forEach(person -> person.getSelectedPlan().getPlanElements().
                stream().filter(planElement -> planElement instanceof Activity).
                forEach(activity -> {
                    if (TripStructureUtils.isStageActivityType(((Activity) activity).getType())) {
                        System.out.println(((Activity) activity).getType());
                        ((Activity) activity).setMaximumDuration(1);
                        //add person with fixed activity to new pop
                        //population.removePerson(person.getId());
                        //fixedPopulation.addPerson(person);
                    } else {
                        //add person to new pop
                        //population.removePerson(person.getId());
                        //fixedPopulation.addPerson(person);
                    }
                })
        );

        return population;
    }



}
