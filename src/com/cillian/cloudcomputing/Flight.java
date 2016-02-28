package com.cillian.cloudcomputing;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {
	
	String passengerID;
	String flightID;
	String from;
	String to;
	String unixArrival;
	String arrivalTime;
	int flightTime;

	
	public Flight(String _passID, String _flightID, String _from, String _to, String _unixArrival, String _flightTime) {
		passengerID = _passID;
		flightID = _flightID;
		
		from = _from;
		to = _to;
		
		unixArrival = _unixArrival;
		arrivalTime = unixToHHMMSS(unixArrival);
		
		flightTime = Integer.parseInt(_flightTime);
	}
	
	private String unixToHHMMSS(String unixTime){
		long unixSeconds = Long.parseLong(unixTime);
		Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // the format of your date
		//sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // give a timezone reference for formating (see comment at the bottom
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	public String getFrom(){
		return from;
	}
	
	public String getPassengerID(){
		return passengerID;
	}
	
	public String getFlightID(){
		return flightID;
	}
	public String getTo(){
		return to;
	}
	String convertArrivalTime(String seconds){
		//Convert unix epoch time to HH:MM:SS
		return new String(seconds);
	}
	
	public String toString(){
		return passengerID + " " + to + " " + from + " " + arrivalTime + " " + flightTime + " mins";
	}
	
}
