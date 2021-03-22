package menu;

import java.util.List;

import com.ss.utopia.service.Driver;

import dao.AirplaneDAO;
import dao.FlightDAO;
import dao.RouteDAO;
import objects.AirplaneType;
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
			menu3SeatsAdd(e);
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

	public static void menu3SeatsAdd(Employee e) throws Exception {
		FlightDAO fd = new FlightDAO();
		Integer categ = fd.seatsAdd(e, false);
		if (categ == 4) {
			employeeMenu3(e);
		}
		employeeMenu3(e);

	}

}
