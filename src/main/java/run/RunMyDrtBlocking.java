package run;

import ch.sbb.matsim.config.SwissRailRaptorConfigGroup;
import ch.sbb.matsim.routing.pt.raptor.RaptorIntermodalAccessEgress;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.population.*;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFareModule;
import org.matsim.contrib.drt.analysis.DrtModeAnalysisModule;
import org.matsim.contrib.drt.routing.MultiModeDrtMainModeIdentifier;
import org.matsim.contrib.drt.run.*;
import org.matsim.contrib.dvrp.run.DvrpModule;
import org.matsim.contrib.dvrp.run.DvrpQSimComponents;
import org.matsim.contrib.freight.FreightConfigGroup;
import org.matsim.contrib.freight.carrier.CarrierPlanXmlWriterV2;
import org.matsim.contrib.freight.carrier.CarrierUtils;
import org.matsim.contrib.freight.utils.FreightUtils;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.AnalysisMainModeIdentifier;
import org.matsim.core.router.MainModeIdentifier;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.drtBlockings.DrtBlockingModule;
import org.matsim.run.RunBerlinScenario;
import org.matsim.run.drt.OpenBerlinIntermodalPtDrtRouterAnalysisModeIdentifier;
import org.matsim.run.drt.OpenBerlinIntermodalPtDrtRouterModeIdentifier;
import org.matsim.run.drt.RunDrtOpenBerlinScenario;
import org.matsim.run.drt.intermodalTripFareCompensator.IntermodalTripFareCompensatorsModule;
import org.matsim.run.drt.ptRoutingModes.PtIntermodalRoutingModesModule;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


public class RunMyDrtBlocking {

    /**
     *
     *
     * @param args should contain the following arguments in the specified order:
     *             1) path to config
     *             2) path to carrier plans
     *             3) path to carrier vehicle types
     *             4) boolean value that determines whether tour planning should be performed before the mobsim. True = tour planning gets performed
     */
    public static void main(String[] args) {

        String configPath = "C:/Users/simon/tubCloud/MA/InputDRT/input_config_reduced.xml";
        String carrierPlans = "C:/Users/simon/tubCloud/MA/InputDRT/carriers_services_openBerlinNet_LichtenbergNord.xml";
        String carrierVehTypes = "C:/Users/simon/tubCloud/MA/InputDRT/carrier_vehicleTypes.xml";
        String newInputPlans = "C:/Users/simon/tubCloud/MA/InputDRT/drtBlockingBase1pct.output_plansFIXED.xml.gz";
        boolean performTourPlanning = false;

        Scenario scenario = prepareScenario(configPath, carrierPlans, carrierVehTypes, performTourPlanning, newInputPlans);

        Controler controler = prepareControler(scenario);

        controler.run();
    }

    public static Scenario prepareScenario(String configPath, String carrierPlans, String carrierVehTypes, boolean performTourplanning, String newInputPlans) {
        Config config = RunDrtOpenBerlinScenario.prepareConfig(new String[]{configPath});
//		config.qsim().setSimStarttimeInterpretation(QSimConfigGroup.StarttimeInterpretation.onlyUseStarttime);
        config.controler().setLastIteration(0);
        config.transit().setUseTransit(false);
        config.plans().setInputFile(newInputPlans);
        config.planCalcScore().addActivityParams(new PlanCalcScoreConfigGroup.ActivityParams(TripStructureUtils.createStageActivityType("pt")).setScoringThisActivityAtAll(false));
        config.planCalcScore().addActivityParams(new PlanCalcScoreConfigGroup.ActivityParams(TripStructureUtils.createStageActivityType("car")).setScoringThisActivityAtAll(false));



//		this is not set by RunBerlinScenario, but vsp consistency checker needs it...
//		config.planCalcScore().setFractionOfIterationsToStartScoreMSA(0.8);

//		config.controler().setOutputDirectory("output/" + config.controler().getRunId() + "/");

        SwissRailRaptorConfigGroup srrConfig = ConfigUtils.addOrGetModule(config, SwissRailRaptorConfigGroup.class);
        //no intermodal access egress for the time being here!
        srrConfig.setUseIntermodalAccessEgress(false);

        FreightConfigGroup freightCfg = ConfigUtils.addOrGetModule(config, FreightConfigGroup.class);
        freightCfg.setCarriersFile(carrierPlans);
        System.out.println("These are our carrier plans: " + freightCfg.getCarriersFile());
        freightCfg.setCarriersVehicleTypesFile(carrierVehTypes);
        System.out.println("These are our carrier vehicle types: " + freightCfg.getCarriersVehicleTypesFile());

        DrtConfigGroup drtCfg = DrtConfigGroup.getSingleModeDrtConfig(config);

        Scenario scenario = RunDrtOpenBerlinScenario.prepareScenario(config);
        FreightUtils.loadCarriersAccordingToFreightConfig(scenario);

        if(performTourplanning){
            try {
                FreightUtils.getCarriers(scenario).getCarriers().values().forEach(carrier -> {
                    CarrierUtils.setCarrierMode(carrier, drtCfg.getMode());
                    CarrierUtils.setJspritIterations(carrier, 20);
                });
                FreightUtils.runJsprit(scenario, freightCfg);
                new File(config.controler().getOutputDirectory()).mkdirs();
                new CarrierPlanXmlWriterV2(FreightUtils.getCarriers(scenario)).write(config.controler().getOutputDirectory() + "carriers_planned.xml");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return scenario;
    }

    public static Controler prepareControler(Scenario scenario){
        Controler controler = RunBerlinScenario.prepareControler( scenario ) ;

        configureDRT(scenario, controler);

        controler.addOverridingModule(new AbstractModule() {
            @Override
            public void install() {
                // use a main mode identifier which knows how to handle intermodal trips generated by the used sbb pt raptor router
                // the SwissRailRaptor already binds its IntermodalAwareRouterModeIdentifier, however drt obviuosly replaces it
                // with its own implementation
                // So we need our own main mode indentifier which replaces both :-(
                bind(MainModeIdentifier.class).to(OpenBerlinIntermodalPtDrtRouterModeIdentifier.class);
                bind(AnalysisMainModeIdentifier.class).to(OpenBerlinIntermodalPtDrtRouterAnalysisModeIdentifier.class);
            }
        });

        controler.addOverridingModule(new IntermodalTripFareCompensatorsModule());
        controler.addOverridingModule(new PtIntermodalRoutingModesModule());

        return controler;
    }

    private static void configureDRT(Scenario scenario, Controler controler) {
        MultiModeDrtConfigGroup multiModeDrtCfg = MultiModeDrtConfigGroup.get(scenario.getConfig());
        DrtConfigGroup drtCfg = DrtConfigGroup.getSingleModeDrtConfig(scenario.getConfig());

        // at the moment, we only configure our 1 drt mode!
        // if you want to use several drt modes AND drt blocking, take care that DrtBlockingModeModule does the QSimBindings for it's mode, so do not use MultiModeDrtModule!
        controler.addOverridingModule(new AbstractModule() {
            @Override
            public void install() {
                install(new DvrpModule());
                install(new DrtModeModule(drtCfg));
//				install(new DrtModeAnalysisModule(drtCfg)); TODO: we have to write a custom OccupancyProfileCalculator that can handle FreightTasks...
                install(new DrtBlockingModule(drtCfg));
                bind(MainModeIdentifier.class).toInstance(new MultiModeDrtMainModeIdentifier(multiModeDrtCfg));
            }
        });
        controler.configureQSimComponents(DvrpQSimComponents.activateAllModes(MultiModeDrtConfigGroup.get(controler.getConfig())));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + multiModeDrtCfg.getParameterSets("drt"));

        // Add drt-specific fare module
        controler.addOverridingModule(new DrtFareModule());
    }


}