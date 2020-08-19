package analysis;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.File;

public class SelectedPlansFilter {

    //File outputPlans = new File("C:/Users/simon/tubCloud/Shared/MA-Meinhardt/berlinv5.5-1pct/baseCase1pct/drtBlockingBase1pct.output_plans.xml.gz");

    private Scenario scenario;
    private String outputPlansFile;

    public SelectedPlansFilter(Scenario scenario, String outputPlansFile) {

        this.scenario = scenario;
        this.outputPlansFile = outputPlansFile;
    }



    public void filterPlans() {

    }

    public void runFilter() {
        //get population
        //Population population = scenario.getPopulation();
        Population population = PopulationUtils.getOrCreateAllpersons(scenario);
        Population outputPopulation = ScenarioUtils.createScenario(ConfigUtils.createConfig()).getPopulation();

        for (Person person : population.getPersons().values()) {

            //copy persons and selected plans
            Person outputPerson = outputPopulation.getFactory().createPerson(person.getId());
            Plan outputPlan = person.getSelectedPlan();
            outputPerson.addPlan(outputPlan);
            outputPopulation.addPerson(outputPerson);
        }

        //write new population
        PopulationWriter writer = new PopulationWriter(outputPopulation);
        writer.write(outputPlansFile);

        System.out.println("Plans filtered!");
    }


}
