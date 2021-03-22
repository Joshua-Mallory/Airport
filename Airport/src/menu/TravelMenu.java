package menu;

import java.util.List;

import com.ss.utopia.service.Driver;

import dao.PassengerDAO;
import dao.RouteDAO;
import objects.Booking;
import objects.Employee;
import objects.User;

public class TravelMenu {

	public static void travelLogin() throws Exception {

		System.out.println("Please enter your Username");
		String user = Driver.scanHandleString();
		System.out.println("Please enter your Password");
		String pass = Driver.scanHandleString();
		Booking b = new Booking();
		User u = new User();
		PassengerDAO pd = new PassengerDAO();
		while (!pd.checkUserCred(user, pass)) {
			System.out.println("Invalid Username or Password");
			System.out.println("Please enter your Username");
			user = Driver.scanHandleString();
			System.out.println("Please enter your Password");
			pass = Driver.scanHandleString();
		}
		u = pd.getUserCred(user, pass);
		System.out.println("Login Successful");
		travelMenuOp(u);
	}

	public static void travelMenuOp(User u) throws Exception {

		System.out.println("1) Book a Ticket\n2) Cancel an Upcoming Trip\n3) Quit to Previous");
		Integer categ = Driver.scanHandleInt();
		switch (categ) {
		case 1:
			travelMenuBook(u);
			break;
		case 2:
			PassengerDAO.travelMenuCancel(u);
			break;
		case 3:
			Driver.menuStart();
			break;
		}
	}

	public static void travelMenuBook(User u) throws Exception {
		System.out.println("Pick the Flight you want to book a ticket for:");
		RouteDAO rd = new RouteDAO();
		List<Object> flights = rd.getEmployeeRouteList();

		int i;
		for (i = 0; i < flights.size(); i++) {
			Employee e = (Employee) flights.get(i);
		}
		System.out.println(i + 1 + ") Quit to previous");

		Integer categ = Driver.scanHandleInt();
		if (categ - 1 == i) {
			travelMenuOp(u);
		} else if (categ <= flights.size()) {
			Employee e = (Employee) (flights.get(categ - 1));
			PassengerDAO.travelMenuBookSeat(e, u);
		}
	}

}
