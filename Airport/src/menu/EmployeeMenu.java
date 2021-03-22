package menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.ss.utopia.service.Driver;

import dao.AirplaneDAO;
import dao.FlightDAO;
import dao.RouteDAO;
import objects.AirplaneType;
import objects.ConnectSetup;
import objects.Employee;
import objects.Flight;

public class EmployeeMenu {

	public static void employeeMenu() throws Exception {

		System.out.println("1)Enter Flights You Manage\n2)Quit to previous ");
		Integer categ = Driver.scanHandleInt();
		switch (categ) {
		case 1:
			employeeMenu2();
			break;
		case 2:
			Driver.menuStart();
			break;
		}
	}

	public static void employeeMenu2() throws Exception {

		RouteDAO rd = new RouteDAO();
		List<Object> flights = rd.getEmployeeRouteList();

		int i;

		for (i = 0; i < flights.size(); i++) {
			Employee e = (Employee) flights.get(i);
			System.out.println((i + 1) + ") " + e.empRoutePrint());
		}
		System.out.println(i + 1 + ") Quit to previous");

		Integer categ = Driver.scanHandleInt();
		if (categ - 1 == i) {
			employeeMenu();
		} else if (categ <= flights.size()) {
			Employee e = (Employee) (flights.get(categ - 1));
			employeeMenu3(e);
		}
	}

	public static void employeeMenu3(Employee e) throws Exception {

		System.out.println(
				"1) View more details about the flight\n2) Update the details of the Flight\n3) Add Seats to Flight\n4) Quit to previous");
		Integer categ = Driver.scanHandleInt();
		switch (categ) {
		case 1:
			emp3Details(e);
			break;
		case 2:
			emp3Update(e);
			break;
		case 3:
			menu3Seats(e);
			break;
		case 4:
			employeeMenu2();
			break;
		}
	}

	public static void flightDetails(Employee e) throws Exception {
		FlightDAO fDOA = new FlightDAO();
		String sql = "select * from flight where route_id= ?";
		Flight f = fDOA.getFlightData(sql, e.getRouteID());
		System.out.println(e.getRouteID());
		System.out.println("You have chosen to view the Flight with Flight Id: " + f.getRoute_id()
				+ " and Departure Airport: " + e.getOriginID() + " and Arrival Airport: " + e.getDestID());
		System.out.println("\nDeparture Airport: " + e.getOriginID() + " | Arrival Airport: " + e.getDestID());
		System.out.println(
				"Departure Date: " + f.getDeparture_time_date() + " | Departure Time: " + f.getDeparture_time_time());
		System.out.print("Arrival time: " + f.getArrival_time_date() + " | Arrival Time: " + f.getArrival_time_time());

		AirplaneDAO ad = new AirplaneDAO();
		AirplaneType at = new AirplaneType();
		at = ad.getAirplaneType((ad.getAirplane(f.getAirplane_id())).getType_id());
		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		System.out.println("\nAvailable Seats by Class:\n1) First -> " + firstPlace);
		System.out.println("2) Business -> " + busPlace);
		System.out.println("3) Economy -> " + econPlace);

	}

	public static void emp3Details(Employee e) throws Exception {
		flightDetails(e);
		System.out.println("4) Return to previous menu");
		Integer categ = Driver.scanHandleInt();
		if (categ == 4) {
			employeeMenu3(e);
		}

	}

	public static void emp3Update(Employee e) throws Exception {
		FlightDAO fd = new FlightDAO();
		fd.flightUpdate(e);
		employeeMenu3(e);
	}

	public static void menu3Seats(Employee e) throws Exception {
		FlightDAO fDOA = new FlightDAO();
		String sql = "select * from flight where route_id= ?";
		Flight f = fDOA.getFlightData(sql, e.getRouteID());
		System.out.println(
				"Pick the Seat Class you want to add seats of, to your flight:\n1) View Flight Details\n1) First\n2) Business\n3) Economy\n4) Quit to cancel operation");
		AirplaneDAO ad = new AirplaneDAO();
		AirplaneType at = new AirplaneType();
		at = ad.getAirplaneType((ad.getAirplane(f.getAirplane_id())).getType_id());
		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		Integer categ = Driver.scanHandleInt();
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		switch (categ) {
		case 1:
			System.out.println(
					"Existing number of First Class seats: " + firstPlace + ".\nHow many would you like to reserve?");
			Integer temp = Driver.scanHandleInt();
			f.setReserved_first(f.getReserved_first() + temp);
			PreparedStatement pstmt2 = conn.prepareStatement("UPDATE flight SET reserved_first = ?  WHERE route_id=?");
			pstmt2.setInt(1, f.getReserved_first());
			pstmt2.setInt(2, f.getRoute_id());
			pstmt2.execute();
			break;
		case 2:
			System.out.println(
					"Existing number of Business Class seats: " + busPlace + ".\nHow many would you like to reserve?");
			Integer temp2 = Driver.scanHandleInt();
			f.setReserved_bus(f.getReserved_bus() + temp2);
			PreparedStatement pstmt3 = conn.prepareStatement("UPDATE flight SET reserved_bus = ?  WHERE route_id=?");
			pstmt3.setInt(1, f.getReserved_bus());
			pstmt3.setInt(2, f.getRoute_id());
			pstmt3.execute();
			break;
		case 3:
			System.out.println("Existing number of Economy Class seats: " + econPlace);
			Integer temp3 = Driver.scanHandleInt();
			f.setReserved_econ(f.getReserved_econ() + temp3);
			PreparedStatement pstmt4 = conn.prepareStatement("UPDATE flight SET reserved_econ = ?  WHERE route_id=?");
			pstmt4.setInt(1, f.getReserved_econ());
			pstmt4.setInt(2, f.getRoute_id());
			pstmt4.execute();
			break;
		case 4:
			employeeMenu3(e);
			break;
		}
		employeeMenu3(e);

	}

}
