package com.ss.utopia.service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws Exception {
		menuStart();
	}

	public static void menuStart() throws Exception {
		System.out.println("Which category of a user are you?\n1) Employee\n2) Administrator\n3) Traveler");
		Integer categ = scanHandleInt();
		String placeholder = null;
		switch (categ) {
		case 1:
			employeeMenu();
			break;
		case 2:
			placeholder = "Administrator";
			break;
		case 3:
			placeholder = "Traveler";
			break;
		}

	}

	public static void employeeMenu() throws Exception {

		System.out.println("1)Enter Flights You Manage\n2)Quit to previous ");
		Integer categ = scanHandleInt();
		switch (categ) {
		case 1:
			employeeMenu2();
			break;
		case 2:
			menuStart();
			break;
		}
	}

	public static void employeeMenu2() throws Exception {

		Route r = new Route();
		List<Object> flights = r.getEmployeeRoute();

		int i;

		for (i = 0; i < flights.size(); i++) {
			Employee e = (Employee) flights.get(i);
			System.out.println((i + 1) + ") " + e.empRoutePrint());
		}
		System.out.println(i + 1 + ") Quit to previous");
		Integer categ = scanHandleInt();
		if (categ - 1 == i) {
			employeeMenu();
		} else if (categ <= flights.size()) {
			employeeMenu3(i, flights);
		}

	}

	/*
	 * 1)View more details about the flight2)Update the details of the Flight 3)Add
	 * Seats to Flight4)Quit to previous (should take you menu EMP2
	 */
	public static void employeeMenu3(int i, List<Object> flights) throws Exception {

		System.out.println(
				"1) View more details about the flight\n2) Update the details of the Flight\n3) Add Seats to Flight\n4) Quit to previous");
		Integer categ = scanHandleInt();
		switch (categ) {
		case 1:
			emp3Details(categ, flights);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			employeeMenu2();
			break;
		}
	}

	public static void emp3Details(int i, List<Object> flights) throws Exception {

		Flight f = new Flight();
		Employee e = (Employee) flights.get(i - 1);
		e = e.getEmployee(e.getRouteID());
		f = f.getFlightData(e.getRouteID());
		System.out.println(e.getRouteID());
		System.out.println("You have chosen to view the Flight with Flight Id: " + f.getRoute_id()
				+ " and Departure Airport: " + e.getOriginName() + " and Arrival Airport: " + e.getDestName());
		System.out.println("\nDeparture Airport: " + e.getOriginName() + "| Arrival Airport: " + e.getDestName());
		System.out.println(
				"Departure Date: " + f.getDeparture_time_date() + "| Departure Time: " + f.getDeparture_time_time());
		System.out
				.print("Arrival time: " + f.getDeparture_time_date() + "| Arrival Time: " + f.getDeparture_time_time());

		Airplane a = new Airplane();
		AirplaneType at = new AirplaneType();
		at = at.getAirplane((a.getAirplane(f.getAirplane_id())).getType_id());
		Integer firstplace = at.getFirst_capacity() - f.getReserved_first();
		Integer busplace = at.getBus_capacity() - f.getReserved_bus();
		Integer econplace = at.getEcon_capacity() - f.getReserved_econ();
		System.out.println("\n Available Seats by Class:\n1) First -> " + firstplace);
		System.out.println("2) Business -> " + busplace);
		System.out.println("3) Economy -> " + econplace);
		System.out.println("4) Return to previous menu");
		Integer categ = scanHandleInt();
		if (categ == 4) {
			employeeMenu3(e.getRouteID(), flights);
		}

	}

	public static void emp3Update(Integer routeID, List<Object> flights) {
		System.out.println("Please enter new Origin Airport and City or enter N/A for no change:");
		String[] orgAirCity = scanHandleString();
		System.out.println("Please enter new Destination Airport and City or enter N/A for no change:");
		String[] desAirCity = scanHandleString();
		System.out.println("Please enter new Departure Date or enter N/A for no change:");
		Date depDate = scanHandleDate();
		System.out.println("Please enter new Arrivale Date or enter N/A for no change:");
		Date arrDate = scanHandleDate();
		System.out.println("Please enter new Departure Time or enter N/A for no change:");
		Time depTime = scanHandleTime();
		System.out.println("Please enter new Arrival Time or enter N/A for no change:");
		Time arrTime = scanHandleTime();

	}

	public static Integer scanHandleInt() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		Integer categ = Integer.parseInt(cat);
		return categ;
	}

	public static Time scanHandleTime() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		String[] temp = cat.split(":");
		Time categ = new Time(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
		return categ;
	}

	public static Date scanHandleDate() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		String[] temp = cat.split("-");
		Date categ = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
		return categ;
	}

	public static String[] scanHandleString() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		return cat.split(" ");
	}
}
