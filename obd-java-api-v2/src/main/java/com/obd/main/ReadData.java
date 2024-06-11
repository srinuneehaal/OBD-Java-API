/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.obd.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.AbsoluteLoadCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.github.pires.obd.commands.fuel.AirFuelRatioCommand;
import com.github.pires.obd.commands.fuel.FindFuelTypeCommand;
import com.github.pires.obd.commands.fuel.FuelLevelCommand;
import com.github.pires.obd.commands.fuel.FuelTrimCommand;
import com.github.pires.obd.commands.fuel.WidebandAirFuelRatioCommand;
import com.github.pires.obd.commands.pressure.FuelPressureCommand;
import com.github.pires.obd.commands.pressure.FuelRailPressureCommand;

public class ReadData {

	public static void main(String[] args) {
		try {

			// https://en.wikipedia.org/wiki/OBD-II_PIDs#Service_09
			// VIN(0902)
			//09 02\r014\r0: 49 02 01 35 4A 36 \r1: 52 57 32 48 35 37 4C \r2: 4C 30 32 30 39 32 34
			
			VinCommand vinCommand = new VinCommand();
			String resVin = "014 0: 49 02 01 37 46 41 \n";
			resVin += "52 57 31 48 37 34 4e 2: 45 30 30 35 32 38 39 >";
			vinCommand.readResult(new ByteArrayInputStream(resVin.getBytes()));
			System.out.println(vinCommand.getName() + "-->" + vinCommand.getFormattedResult());

			// https://en.wikipedia.org/wiki/OBD-II_PIDs#Service_01
			// RPM(010C)
			RPMCommand rpmCommand = new RPMCommand();
			String resRPM = "41 0C 0B 54 55 55 55 >";
			rpmCommand.readResult(new ByteArrayInputStream(resRPM.getBytes()));
			rpmCommand.performCalculations();
			System.out.println(rpmCommand.getName() + "-->" + rpmCommand.getFormattedResult());

//			Throttle position(0143)	
			AbsoluteLoadCommand absoluteLoadCommand = new AbsoluteLoadCommand();
			String resAbsLoad = "41 43 00 48 55 55 55";
			absoluteLoadCommand.readResult(new ByteArrayInputStream(resAbsLoad.getBytes()));
			absoluteLoadCommand.performCalculations();
			System.out.println(absoluteLoadCommand.getName() + "-->" + absoluteLoadCommand.getFormattedResult());

			// Run time since engine start(011F)
			RuntimeCommand runtimeCommand = new RuntimeCommand();
			String resRuntime = "41 1F 00 12 55 55 55 41 1F 00 12 55 55 55";
			runtimeCommand.readResult(new ByteArrayInputStream(resRuntime.getBytes()));
			runtimeCommand.performCalculations();
			System.out.println(runtimeCommand.getName() + "-->" + runtimeCommand.getFormattedResult());

			// 01 44\r41 44 7E F3 55 55 55
			// Commanded Air-Fuel Equivalence Ratio (lambda,λ) (0144)
			AirFuelRatioCommand airFuelRatioCommand = new AirFuelRatioCommand();
			String resAirFuel = "41 44 7E F3 55 55 55";
			airFuelRatioCommand.readResult(new ByteArrayInputStream(resAirFuel.getBytes()));
			airFuelRatioCommand.performCalculations();
			System.out.println(airFuelRatioCommand.getName() + "-->" + airFuelRatioCommand.getFormattedResult());

			// 01 51\r41 51 01 55 55 55 55
			//Fuel Type	(0151)
			FindFuelTypeCommand findFuelTypeCommand=new FindFuelTypeCommand();
			String resFuelType = "41 51 01 55 55 55 55";
			findFuelTypeCommand.readResult(new ByteArrayInputStream(resFuelType.getBytes()));
			findFuelTypeCommand.performCalculations();
			System.out.println(findFuelTypeCommand.getName() + "-->" + findFuelTypeCommand.getFormattedResult());
			
			
			//01 2F\r41 2F 7B 55 55 55 55
			//Fuel Tank Level Input	(012F)
			FuelLevelCommand  fuelLevelCommand=new FuelLevelCommand();
			String resFuelTankLevel = "41 2F 7B 55 55 55 55";
			fuelLevelCommand.readResult(new ByteArrayInputStream(resFuelTankLevel.getBytes()));
			fuelLevelCommand.performCalculations();
			System.out.println(fuelLevelCommand.getName() + "-->" + fuelLevelCommand.getFormattedResult());
			
			//Short term fuel trim (STFT)—Bank 1 (0106)
			//01 06\r41 06 7C 55 55 55 55
			FuelTrimCommand fuelTrimCommand=new FuelTrimCommand();
			String resFuelTrim = "41 06 7C 55 55 55 55";
			fuelTrimCommand.readResult(new ByteArrayInputStream(resFuelTrim.getBytes()));
			fuelTrimCommand.performCalculations();
			System.out.println(fuelTrimCommand.getName() + "-->" + fuelTrimCommand.getFormattedResult());
			
			//	Fuel Rail Gauge Pressure (diesel, or gasoline direct injection)(0123)
			//01 23\r41 23 01 7E 55 55 55
			FuelRailPressureCommand fuelRailPressureCommand=new FuelRailPressureCommand();
			String resFuelPressure = "41 23 01 7E 55 55 55";
			fuelRailPressureCommand.readResult(new ByteArrayInputStream(resFuelPressure.getBytes()));
			//fuelRailPressureCommand.performCalculations();
			System.out.println(fuelRailPressureCommand.getName() + "-->" + fuelRailPressureCommand.getFormattedResult());

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
