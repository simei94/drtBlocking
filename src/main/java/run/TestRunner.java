/* *********************************************************************** *
 * project: org.matsim.*
 * Controler.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package run;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.Controler;
import org.matsim.run.drtBlocking.RunDrtBlocking;

public class TestRunner {

	public static void main(String[] args) {

		String configPath = "C:/Users/simon/tubCloud/MA/InputDRT/input_config_reduced.xml";
		String carrierPlans = "C:/Users/simon/tubCloud/MA/InputDRT/carriers_services_openBerlinNet_Lichtenberg Nord.xml";
		String carrierVehTypes = "C:/Users/simon/tubCloud/MA/InputDRT/carrier_vehicleTypes.xml";
		boolean performTourPlanning = false;

		//Scenario scenario = RunDrtBlocking.prepareScenario(configPath, carrierPlans, carrierVehTypes, performTourPlanning);

		//scenario.getConfig().controler().setOutputDirectory("output-test");

		//Controler controler = RunDrtBlocking.prepareControler(scenario);

		//controler.run();



	}

}
