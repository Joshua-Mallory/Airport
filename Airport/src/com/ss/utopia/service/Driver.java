package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
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
			travelerMenu();
			break;
		}

	}

	public static void travelerMenu() throws Exception {
		System.out.println("Please enter your username: ");
		String user = scanHandleString();
		System.out.println("Please enter your password: ");
		String pass = scanHandleString();
		Traveler t = new Traveler();
		while (!t.checkUserCred(user, pass)) {
			System.out.println("Invalid Username or Password. Try again.");
			System.out.println("Please enter your username: ");
			user = scanHandleString();
			System.out.println("Please enter your password: ");
			pass = scanHandleString();
		}
		User u = new User();
		u.getUserCred(user, pass);
		System.out.print(u.getFamily_name());
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
		List<Object> flights = r.getEmployeeRouteList();

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
			Employee e = (Employee) (flights.get(categ - 1));
			employeeMenu3(e);
		}

	}

	/*
	 * 1)View more details about the flight2)Update the details of the Flight 3)Add
	 * Seats to Flight4)Quit to previous (should take you menu EMP2
	 */
	public static void employeeMenu3(Employee e) throws Exception {

		System.out.println(
				"1) View more details about the flight\n2) Update the details of the Flight\n3) Add Seats to Flight\n4) Quit to previous");
		Integer categ = scanHandleInt();
		switch (categ) {
		case 1:
			emp3Details(e);
			break;
		case 2:
			emp3Update(e);
			break;
		case 3:
			break;
		case 4:
			employeeMenu2();
			break;
		}
	}

	public static void emp3Details(Employee e) throws Exception {

		Flight f = new Flight();
		f = f.getFlightData(e.getRouteID());
		System.out.println(e.getRouteID());
		System.out.println("You have chosen to view the Flight with Flight Id: " + f.getRoute_id()
				+ " and Departure Airport: " + e.getOriginID() + " and Arrival Airport: " + e.getDestID());
		System.out.println("\nDeparture Airport: " + e.getOriginID() + " | Arrival Airport: " + e.getDestID());
		System.out.println(
				"Departure Date: " + f.getDeparture_time_date() + " | Departure Time: " + f.getDeparture_time_time());
		System.out.print("Arrival time: " + f.getArrival_time_date() + " | Arrival Time: " + f.getArrival_time_time());

		Airplane a = new Airplane();
		AirplaneType at = new AirplaneType();
		at = at.getAirplane((a.getAirplane(f.getAirplane_id())).getType_id());
		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		System.out.println("\nAvailable Seats by Class:\n1) First -> " + firstPlace);
		System.out.println("2) Business -> " + busPlace);
		System.out.println("3) Economy -> " + econPlace);
		System.out.println("4) Return to previous menu");
		Integer categ = scanHandleInt();
		if (categ == 4) {
			employeeMenu3(e);
		}

	}

	public static void emp3Update(Employee e) throws Exception {
		Flight f = new Flight();
		f = f.getFlightData(e.getRouteID());
		Route r = new Route();
		System.out.println("Please enter new Origin Airport and City or enter N/A for no change:");
		String origin = scanCheckNA();
		String holdAir = null;
		String holdCity = null;
		String[] orgAirCity = null;
		if (!origin.equals("N/A")) {
			orgAirCity = scanHandleString(origin);
			while (!r.employeeRouteCheck(orgAirCity[0], orgAirCity[1]) && !origin.equals("N/A")) {
				System.out
						.println("Invalid Input: Please enter new Origin Airport and City or enter N/A for no change:");
				origin = scanCheckNA();
				if (!origin.equals("N/A"))
					orgAirCity = scanHandleString(origin);
			}
			holdAir = orgAirCity[0];
			holdCity = orgAirCity[1];
		}
		if (origin.equals("N/A")) {
			holdAir = e.getOriginID();
			holdCity = e.getOriginName();
		}

		System.out.println(origin);
		System.out.println("Please enter new Destination Airport and City or enter N/A for no change:");
		String destAir = null;
		String destCity = null;
		String dest = scanCheckNA();
		String[] desAirCity = null;
		if (!dest.equals("N/A")) {
			desAirCity = scanHandleString(dest);
			while (!r.employeeRouteCheck(desAirCity[0], desAirCity[1]) && !dest.equals("N/A")) {
				System.out.println(
						"Invalid Input: Please enter new Destination Airport and City or enter N/A for no change:");
				dest = scanCheckNA();
				if (!dest.equals("N/A"))
					desAirCity = scanHandleString(dest);
			}
			destAir = desAirCity[0];
			destCity = desAirCity[1];
		}
		if (dest.equals("N/A")) {
			destAir = e.getDestID();
			destCity = e.getDestName();
		}
		System.out.println("Please enter new Departure Date or enter N/A for no change:");
		String depD = scanCheckNA();
		String depDate = null;
		if (!depD.equals("N/A")) {
			depDate = scanHandleDate(depD);
		}
		System.out.println(depDate);
		if (depDate == null)
			depDate = String.valueOf(f.getDeparture_time_date());
		System.out.println("Please enter new Arrivale Date or enter N/A for no change:");
		String arrD = scanCheckNA();
		String arrDate = null;
		if (!arrD.equals("N/A")) {
			arrDate = scanHandleDate(arrD);
		}
		if (arrDate == null)
			arrDate = String.valueOf(f.getArrival_time_date());
		System.out.println("Please enter new Departure Time or enter N/A for no change:");
		String dept = scanCheckNA();
		Time depTime = null;
		if (!dept.equals("N/A")) {
			depTime = scanHandleTime(dept);
		}
		if (depTime == null)
			depTime = f.getDeparture_time_time();
		System.out.println("Please enter new Arrival Time or enter N/A for no change:");
		String arrT = scanCheckNA();
		Time arrTime = null;
		if (!arrT.equals("N/A")) {
			arrTime = scanHandleTime(arrT);
		}
		if (arrTime == null)
			arrTime = f.getArrival_time_time();
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		e = new Employee(e.getRouteID(), holdAir, holdCity, destAir, destCity);
		System.out.print(e.empRoutePrint());
		String depDateTime = depDate + " " + String.valueOf(depTime);
		String arrDateTime = arrDate + " " + String.valueOf(arrTime);
		// System.out.println(depDateTime + "\n" + arrDateTime);
		java.sql.Timestamp depDT = java.sql.Timestamp.valueOf(depDateTime);
		java.sql.Timestamp arrDT = java.sql.Timestamp.valueOf(arrDateTime);
		System.out.println(e.getRouteID());
		PreparedStatement pstmt = conn
				.prepareStatement("UPDATE route SET origin_id = ? ,destination_id = ? WHERE id=?");
		PreparedStatement pstmt2 = conn
				.prepareStatement("UPDATE flight SET departure_time = ?, arrival_time = ? WHERE route_id=?");
		pstmt.setString(1, e.getOriginID());
		pstmt.setString(2, e.getDestID());
		pstmt.setInt(3, e.getRouteID());
		pstmt2.setTimestamp(1, depDT);
		pstmt2.setTimestamp(2, arrDT);
		pstmt2.setInt(3, e.getRouteID());
		pstmt.execute();
		pstmt2.execute();
		conn.commit();
		System.out.println("Successfully Updated");
		employeeMenu3(e);
	}

	public static void menu3Seats(Employee e) throws Exception {
		Flight f = new Flight();
		f = f.getFlightData(e.getRouteID());
		System.out.println(
				"Pick the Seat Class you want to add seats of, to yourflight:\n1) First\n2) Business\n3) Economy\n4) Quit to cancel operation");
		// COME BACK TO THISSSSSSSSSSS
		Airplane a = new Airplane();
		AirplaneType at = new AirplaneType();
		at = at.getAirplane((a.getAirplane(f.getAirplane_id())).getType_id());
		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		Integer categ = scanHandleInt();
		switch (categ) {
		case 1:
			System.out.println("Existing number of First Class seats: " + firstPlace);
			break;
		case 2:
			System.out.println("Existing number of Business Class seats: " + busPlace);
			break;
		case 3:
			System.out.println("Existing number of Economy Class seats: " + econPlace);
			break;
		case 4:
			employeeMenu3(e);
			break;
		}
	}

	public static Integer scanHandleInt() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		Integer categ = Integer.parseInt(cat);
		return categ;
	}

	public static String scanCheckNA() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		return cat;
	}

	public static Time scanHandleTime(String cat) {
		String[] temp = cat.split(":");
		Time categ = new Time(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
		return categ;
	}

	public static String scanHandleDate(String cat) {
		return cat;
	}

	public static String scanHandleString() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		return cat;
	}

	public static String[] scanHandleString(String cat) {
		return cat.split(" ", 2);
	}
}
