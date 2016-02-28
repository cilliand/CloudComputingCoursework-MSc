package com.cillian.cloudcomputing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

public class CloudComputing {
	
	static String inputFile = "";
	static String secondInput = "";
	static String outputFile = "";
	
	public static void main(String[] args) throws FileNotFoundException {

		try {
			inputFile = args[0];
			secondInput = args[1];
			outputFile = args[2];
		} catch (Exception e) {
			System.out.println("Input or output file missing.");
			System.exit(0);
		}

		try {
			// Read input from file
			task1();

			task2();

			 task3();

			 task4();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// TASK 1 -- Determine the number of flights from each airport, include
	// a list of any airports not used.
	public static void task1() {
		ConcurrentSkipListMap<String, Integer> simpleMap = new ConcurrentSkipListMap<String, Integer>();
		try {
			// Read input
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String line;
			ArrayList<String> unMatchedAirports = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");

				if (regexThis(split, 1)) {
					if (simpleMap.containsKey(split[2])) {
						// calculate number of flights from each airport
						simpleMap.put(split[2], simpleMap.get(split[2]) + 1);
					} else {
						simpleMap.put(split[2], 1);
					}
				} else {
					unMatchedAirports.add(split[2]);
				}
			}
			br.close();

			// Write output to file
			FileWriter fw = new FileWriter(outputFile);
			for (Entry<String, Integer> entry : simpleMap.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				fw.write(entry.getKey() + " : " + entry.getValue());
				fw.write(System.getProperty("line.separator"));
			}

			System.out.println("");
			System.out.println("Unused airports (" + unMatchedAirports.size() + "): ");
			fw.write("Unused airports (" + unMatchedAirports.size() + "): " + System.getProperty("line.separator"));
			for (String airport : unMatchedAirports) {
				if (!simpleMap.containsKey(airport)) {
					System.out.print(airport + ", ");
					fw.write(airport + ", ");
				}
			}

			System.out.println("");
			System.getProperty("line.separator");

			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TASK 2 Create a list of flights based on the Flight id, this output
	// should include the passenger Id, relevant IATA/FAA codes, the departure
	// time,
	// the arrival time (times to be converted to HH:MM:SS format), and the
	// flight times.
	static HashMap<String, List<Flight>> flights = new HashMap<String, List<Flight>>();

	public static void task2() {
		try {
			// Read input from file
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String line;

			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				if (regexThis(split, 2)) {
					try {
						if (flights.get(split[1]) == null) {
							flights.put(split[1], new ArrayList<Flight>());
						}
						flights.get(split[1])
								.add(new Flight(split[0], split[1], split[2], split[3], split[4], split[5]));
					} catch (Exception e) {
						System.out.println("Failed to add <" + split[2].toString() + "> to map.");
					}
				}
			}
			br.close();
			System.out.println("");
			for (Map.Entry<String, List<Flight>> entry : flights.entrySet()) {
				System.out.println(entry.getKey());
				for (Flight detail : entry.getValue()) {
					System.out.println(detail);
				}
				System.out.println("");
			}
			// System.out.println(putInt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	// TASK 3 -- Calculate the number of passengers on each flight.
	public static void task3() {
		System.out.println("Passenger Count");
		System.out.println("---------------------------------");
		try {
			HashMap<String, Integer> passengerCount = new HashMap<String, Integer>();
			BufferedReader br = new BufferedReader(new FileReader(inputFile));

			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				if(regexThis(split, 3)){
					if (passengerCount.get(split[1]) == null) {
						passengerCount.put(split[1], 1);
					} else {
						passengerCount.put(split[1], passengerCount.get(split[1]) + 1);
					}
				}
			}
			br.close();
			for (Map.Entry<String, Integer> entry : passengerCount.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// for(Map.Entry<String, List<Flight>> entry: kvPairs.entrySet()){
		// List<Flight> temp = new ArrayList<Flight>(entry.getValue());
		// System.out.println(entry.getValue()+": "+temp.size());
		// }
	}

	
	
	// TASK 4 -- Calculate the line-of-sight (nautical) miles for
	// each flight and the total travelled by each passenger.
	public static void task4() {
		// Read in top 30 airports
		// For each passenger id, for each flight calculate distance.
		// Reduce each passenger to be combined (in case some passengers have
		// more than one flight)
		System.out.println("");
		System.out.println("Distance Travelled Per Passenger");
		System.out.println("---------------------------------");
		try {
			HashMap<String, Airport> airports = new HashMap<String, Airport>();
			BufferedReader br = new BufferedReader(new FileReader(secondInput));
			String line;

			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				if(regexThis(split, 4)){
					try {
						if (airports.get(split[1]) == null) {
							airports.put(split[1], new Airport(split[1], split[2], split[3]));
						}
						 //airports.get(split[1]).add(new Airport(split[1], split[2], split[3]));
					} catch (Exception e) {
						System.out.println("Failed to add <" + split[0] + "> to map.");
					}
				}
			}
			br.close();
			System.out.println("");
			HashMap<String, Double> distances = new HashMap<String, Double>();
			/**
			 * For each flight calculate distance between airports.
			 */
			for (Map.Entry<String, List<Flight>> entry : flights.entrySet()) {
				double totalDistance = 0.0;
				for (Flight aFlight : entry.getValue()) {
					try {
						double x1 = ((Airport) airports.get(aFlight.getFrom())).getLat();
						double y1 = ((Airport) airports.get(aFlight.getFrom())).getLon();
						double x2 = ((Airport) airports.get(aFlight.getTo())).getLat();
						double y2 = ((Airport) airports.get(aFlight.getTo())).getLon();
						
						double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
						// System.out.println(aFlight.getFlightID() + ": " +
						// distance + " Naut. Miles");
						totalDistance += distance;
						/**
						 * Save each distance in a map
						 */
						distances.put(entry.getKey(), totalDistance);
					} catch (Exception e) {
						//System.out.println("Couldn't find " + airports.get(aFlight.getTo()));
					}
				}

			}
			for (Map.Entry<String, Double> entry : distances.entrySet()) {
				System.out.println(entry.getKey() + " traveled " + entry.getValue());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	//Method use to verify input string is valid, all elements must be valid.
	// Input: String to verify, number of task to verify.
	private static boolean regexThis(String[] split, int task) {
		switch (task) {
		case 1:
			if(split.length < 3)
				return false;
			return split[2].matches("[A-Z]{3}");
		case 2:
			if(split.length < 6)
				return false;
			return split[0].matches("[A-Z]{3}[0-9]{4}[A-Z]{2}[0-9]{1}") & split[1].matches("[A-Z]{3}[0-9]{4}[A-Z]{1}")
					& split[2].matches("[A-Z]{3}") & split[3].matches("[A-Z]{3}") & split[4].matches("\\d{10}")
					& split[5].matches("\\d{1,4}");
		case 3:
			if(split.length < 2)
				return false;
			return split[1].matches("[A-Z]{3}[0-9]{4}[A-Z]{1}");
		case 4: 
			if(split.length < 4)
				return false;
			//Validating with only between 3 and 13 digits results if very few results
			// return 	split[1].matches("[A-Z]{3}") & 
			//			split[2].matches("[+-]?\\d{3,13}\\.?\\d{3,13}") & 
			//		 	split[3].matches("[+-]?\\d{3,13}\\.?\\d{3,13}");
			
			// Using this less specific implementation instead.
			return 	split[1].matches("[A-Z]{3}") &
					split[2].matches("[+-]?\\d*\\.?\\d*") & 
					split[3].matches("[+-]?\\d*\\.?\\d*"); 
		default:
			return false;
		}
	}
}
