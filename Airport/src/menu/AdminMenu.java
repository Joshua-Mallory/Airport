package menu;

import java.util.List;

import com.ss.utopia.service.Driver;

import dao.FlightDAO;
import dao.RouteDAO;
import objects.Employee;

public class AdminMenu {

	public static void adminMenu() throws Exception {
		System.out.println(
				"1) Add/Update/Delete/Read Flights\n2) Add/Update/Delete/Read Seats\n3) Add/Update/Delete/Read Tickets and Passengers\n4) Add/Update/Delete/Read Airports\n5) Add/Update/Delete/Read Travelers\n6) Add/Update/Delete/Read Employees\n7) Over-ride Trip Cancellation for a ticket.");
		Integer categ = Driver.scanHandleInt();
		switch (categ) {
		case 1:
			flights();
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		}
	}

	public static void flights() throws Exception {
		System.out.println("Select a flight to view more details/update or delete it.");
		RouteDAO rd = new RouteDAO();
		Employee e = new Employee();
		List<Object> flights = rd.getEmployeeRouteList();
		int i;
		for (i = 0; i < flights.size(); i++) {
			e = (Employee) flights.get(i);
			System.out.println((i + 1) + ") " + e.empRoutePrint());
		}
		System.out.println(i + 1 + ") Add a new flight");
		System.out.println(i + 2 + ") Quit to previous");
		Integer categ = Driver.scanHandleInt();
		if (categ - 2 == i) {
			adminMenu();
		} else if (categ - 1 == i) {
			flightsAdd();
		} else if (categ <= flights.size()) {
			e = (Employee) (flights.get(categ - 1));
			System.out.println(
					"Would you like to\n1) View more Details\n2) Update this flight \n3) Delete this flight \n4) Quit to Admin Menu");
			Integer temp = Driver.scanHandleInt();
			FlightDAO fd = new FlightDAO();
			switch (temp) {
			case 1:
				EmployeeMenu.flightDetails(e);
				break;
			case 2:
				String sql = "select * from flight where route_id= ?";
				fd.insertFlightDetails(e, fd.getFlightData(sql, e.getRouteID()));
				break;
			case 3:
				fd.deleteFlight();
				break;
			case 4:
				adminMenu();
				break;
			}
		}
	}

	public static void flightsAdd() throws Exception {
		FlightDAO fd = new FlightDAO();
		RouteDAO rd = new RouteDAO();
		System.out.println("What is the three letter Airport Origin?");
		String orig = Driver.scanHandleString();
		System.out.println("What is the three letter Airport Destination?");
		String dest = Driver.scanHandleString();
		System.out.println("What airplane type would you like to use?");
		Integer airType = Driver.scanHandleInt();
		fd.insertFlight(airType, orig, dest);
		Employee e = rd.getEmployee(orig, dest);
	}
}
