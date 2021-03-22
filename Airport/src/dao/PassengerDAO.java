package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ss.utopia.service.Driver;

import menu.AdminMenu;
import menu.EmployeeMenu;
import menu.TravelMenu;
import objects.AirplaneType;
import objects.Booking;
import objects.Booking_User;
import objects.ConnectSetup;
import objects.Employee;
import objects.Flight;
import objects.Flight_Bookings;
import objects.Passenger;
import objects.Route;
import objects.User;

public class PassengerDAO {

	public Connection conSet() {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = null;
		try {
			conn = cs.getConnection();
		} catch (Exception e) {
			System.out.println("Bad Connection");
			e.printStackTrace();
		}
		return conn;
	}

	public User getUserCred(String user, String pass) throws Exception {
		ResultSet rs = commonCheck(user, pass);
		rs.next();
		User u = new User(rs.getInt("id"), rs.getInt("role_id"), rs.getString("given_name"),
				rs.getString("family_name"), rs.getString("username"), rs.getString("email"), rs.getString("password"),
				rs.getString("phone"));
		return u;
	}

	public boolean checkUserCred(String user, String pass) throws Exception {
		ResultSet rs = commonCheck(user, pass);
		return rs.next();
	}

	public ResultSet commonCheck(String user, String pass) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user where username = ? and password = ?");
		pstmt.setString(1, user);
		pstmt.setString(2, pass);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public Passenger getPassengerInfo(Integer book_id) throws Exception {
		Connection conn = conSet();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM passenger where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Passenger p = new Passenger(rs.getInt("id"), rs.getInt("booking_id"), rs.getString("given_name"),
				rs.getString("family_name"), rs.getDate("dob"), rs.getString("gender"), rs.getString("address"),
				rs.getString("seat_type"));
		return p;
	}

	public List<Object> getPassengerList() throws Exception {
		Connection conn = conSet();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM passenger");
		ResultSet rs = pstmt.executeQuery();
		List<Object> pl = new ArrayList<Object>();
		while (rs.next()) {
			Passenger p = new Passenger(rs.getInt("id"), rs.getInt("booking_id"), rs.getString("given_name"),
					rs.getString("family_name"), rs.getDate("dob"), rs.getString("gender"), rs.getString("address"),
					rs.getString("seat_type"));
			pl.add(p);
		}
		return pl;
	}

	public ResultSet bookingCommon(Integer user_id) throws SQLException {
		Connection conn = conSet();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking_user where user_id= ? ");
		pstmt.setInt(1, user_id);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public Booking_User getBookingFromUser(Integer user_id) throws Exception {
		ResultSet rs = bookingCommon(user_id);
		Booking_User bu = new Booking_User();
		while (rs.next()) {
			bu = new Booking_User(rs.getInt("booking_id"), rs.getInt("user_id"));
		}
		return bu;
	}

	public List<Object> getBookingAdminList() throws Exception {
		Connection conn = conSet();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking;");
		ResultSet rs = pstmt.executeQuery();
		List<Object> books = new ArrayList<Object>();
		while (rs.next()) {
			Booking b = new Booking(rs.getInt("id"), rs.getBoolean("is_active"), rs.getString("confirmation_code"));
			books.add(b);
		}
		return books;
	}

	public List<Object> getBookingUserList(Integer user_id) throws Exception {
		ResultSet rs = bookingCommon(user_id);
		List<Object> books = new ArrayList<Object>();
		while (rs.next()) {
			Booking_User bu = new Booking_User(rs.getInt("booking_id"), rs.getInt("user_id"));
			System.out.println(bu.getBooking_id());
			books.add(bu);
		}
		return books;
	}

	public boolean checkConf(String conf) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking where confirmation_code = ? ");
		pstmt.setString(1, conf);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}

	public Booking getBooking(String conf) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking where confirmation_code= ? ");
		pstmt.setString(1, conf);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Booking b = new Booking(rs.getInt("id"), rs.getBoolean("is_active"), rs.getString("confirmation_Code"));
		return b;
	}

	public static void travelMenuBookSeat(Employee e, User u) throws Exception {

		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		Booking_User bu = new Booking_User();
		PassengerDAO pd = new PassengerDAO();
		bu = pd.getBookingFromUser(u.getId());
		List<Object> buList = pd.getBookingUserList(u.getId());
		String sql = "select * from flight where route_id= ?";
		FlightDAO fDOA = new FlightDAO();
		Flight f = fDOA.getFlightData(sql, e.getRouteID());
		ResultSet rs;
		boolean checkExist = false;
		if (buList.size() > 0) {
			for (int i = 0; i < buList.size(); i++) {
				PreparedStatement pstmtCheck = conn
						.prepareStatement("select * from flight_bookings where flight_id=? and booking_id=?");
				pstmtCheck.setInt(1, f.getId());
				bu = (Booking_User) buList.get(i);
				pstmtCheck.setInt(2, bu.getBooking_id());
				rs = pstmtCheck.executeQuery();
				if (rs.next()) {
					PreparedStatement pstmt = conn.prepareStatement(
							"Insert into passenger values (null,?,'test','test','2000/01/01','M','null island','N');");
					pstmt.setInt(1, bu.getBooking_id());
					pstmt.execute();
					conn.commit();
					checkExist = true;
				}
			}
		}

		AirplaneType at = new AirplaneType();
		AirplaneDAO ad = new AirplaneDAO();

		at = ad.getAirplaneType((ad.getAirplane(f.getAirplane_id())).getType_id());

		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		Random gen = new Random();
		String testNum = Integer.toString(gen.nextInt(899999) + 100001);
		if (checkExist == false) {

			PreparedStatement pstmtBook = conn.prepareStatement("Insert into booking values (null,1,?);");
			pstmtBook.setString(1, testNum);
			pstmtBook.execute();
			conn.commit();
			Booking b = new Booking();
			b = pd.getBooking(testNum);
			PreparedStatement pstmt = conn.prepareStatement(
					"Insert into passenger values (null,?,'test','test','2000/01/01','M','null island','N');");

			pstmt.setInt(1, b.getId());
			pstmt.execute();
			PreparedStatement pstmt3 = conn.prepareStatement("Insert into booking_user values (?,?);");
			pstmt3.setInt(1, b.getId());
			pstmt3.setInt(2, u.getId());
			pstmt3.execute();
			PreparedStatement pstmt6 = conn
					.prepareStatement("select * from flight_bookings where flight_id=? and booking_id=?");
			pstmt6.setInt(1, f.getId());
			pstmt6.setInt(2, b.getId());

			ResultSet rs2 = pstmt6.executeQuery();

			if (!rs2.next()) {
				PreparedStatement pstmt5 = conn.prepareStatement(
						"Insert into flight_bookings values ((select id from flight where id=?),(select id from booking where id=?));");
				pstmt5.setInt(1, f.getId());
				pstmt5.setInt(2, b.getId());
				pstmt5.execute();
			}
		}
		List<String> placeW = new ArrayList<String>();
		List<String> placeComp = new ArrayList<String>();
		placeComp.add("First");
		placeComp.add("Business");
		placeComp.add("Economy");
		int i;
		if (firstPlace > 0) {
			placeW.add("First");

		}
		if (busPlace > 0) {
			placeW.add("Business");
		}
		if (econPlace > 0) {
			placeW.add("Economy");
		}
		EmployeeMenu.flightDetails(e);
		System.out.println("\n");
		TimeUnit.SECONDS.sleep(1);
		for (i = 0; i < placeW.size(); i++) {
			System.out.println((i + 1) + ") " + placeW.get(i));
		}

		System.out.println(i + 1 + ") Quit to previous");
		Integer categ = Driver.scanHandleInt();
		if (categ - 1 == i) {
			if (!checkExist) {
				conn.rollback();
				Booking b = new Booking();
				b = pd.getBooking(testNum);
				System.out.println(b.getId());
				PreparedStatement pstmtRoll = conn.prepareStatement("Delete from booking where id = ?;");
				pstmtRoll.setInt(1, b.getId());
				pstmtRoll.execute();
				conn.commit();
			}
			TravelMenu.travelMenuOp(u);
		} else if (categ <= placeW.size()) {
			System.out.println(i);
			for (int j = 0; j < placeW.size(); j++) {
				if (placeW.get(categ - 1).equals(placeComp.get(j))) {

					switch (placeW.get(categ - 1)) {
					case "First":
						System.out.println("You have booked a First Class seat.");
						f.setReserved_first((f.getReserved_first() + 1));
						PreparedStatement pstmt2 = conn
								.prepareStatement("UPDATE flight SET reserved_first = ?  WHERE route_id=?");
						pstmt2.setInt(1, f.getReserved_first());
						pstmt2.setInt(2, f.getRoute_id());
						pstmt2.execute();
						PreparedStatement pstmt3 = conn
								.prepareStatement("UPDATE passenger SET seat_type=?  WHERE seat_type=?");
						pstmt3.setString(1, "F");
						pstmt3.setString(2, "N");
						pstmt3.execute();
						break;
					case "Business":
						System.out.println("You have booked a Business Class seat.");
						f.setReserved_bus((f.getReserved_bus() + 1));
						PreparedStatement pstmt9 = conn
								.prepareStatement("UPDATE flight SET reserved_bus = ?  WHERE route_id=?");
						pstmt9.setInt(1, f.getReserved_bus());
						pstmt9.setInt(2, f.getRoute_id());
						pstmt9.execute();
						f.getRoute_id();
						PreparedStatement pstmtS = conn
								.prepareStatement("UPDATE passenger SET seat_type=?  WHERE seat_type=?");
						pstmtS.setString(1, "B");
						pstmtS.setString(2, "N");
						pstmtS.execute();
						break;
					case "Economy":
						f.setReserved_econ((f.getReserved_econ() + 1));
						System.out.println("You have booked an Economy Class seat.");
						PreparedStatement pstmt4 = conn
								.prepareStatement("UPDATE flight SET reserved_econ = ?  WHERE route_id=?");
						pstmt4.setInt(1, f.getReserved_econ());
						pstmt4.setInt(2, f.getRoute_id());
						pstmt4.execute();
						f.getRoute_id();
						PreparedStatement pstmt0 = conn
								.prepareStatement("UPDATE passenger SET seat_type=?  WHERE seat_type=?");
						pstmt0.setString(1, "E");
						pstmt0.setString(2, "N");
						pstmt0.execute();
						break;
					}
				}
			}
			conn.commit();
			TravelMenu.travelMenuOp(u);
		}

	}

	public static void travelMenuCancel(User u) throws Exception {

		Booking_User bu = new Booking_User();
		PassengerDAO pd = new PassengerDAO();
		List<Object> buList = pd.getBookingUserList(u.getId());
		if (buList.size() == 0) {
			System.out.println("You currently have no active flights.");
			TravelMenu.travelMenuOp(u);
		}
		System.out.println("Pick the Flight you want to cancel a ticket for:");
		Flight_Bookings fb = new Flight_Bookings();
		List<Object> flightb = new ArrayList<Object>();
		String sql = "select * from flight where id = ?;";
		Flight f = new Flight();
		FlightDAO fDOA = new FlightDAO();

		for (int k = 0; k < buList.size(); k++) {
			RouteDAO rd = new RouteDAO();
			bu = (Booking_User) buList.get(k);
			FlightDAO fd = new FlightDAO();
			fb = fd.getFlightbook(bu.getBooking_id());
			f = fDOA.getFlightData(sql, fb.getFlight_id());
			Route r = new Route();
			Employee e = rd.getCustomerRoute(f.getRoute_id());
			System.out.println((k + 1) + ") " + e.empRoutePrint());
			flightb.add(fb);
		}

		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		Integer selec = Driver.scanHandleInt();
		fb = (Flight_Bookings) flightb.get(selec - 1);
		f = fDOA.getFlightData(sql, fb.getFlight_id());
		RouteDAO rd = new RouteDAO();
		Employee e = rd.getCustomerRoute(f.getRoute_id());
		Passenger p = new Passenger();
		p = pd.getPassengerInfo(fb.getBooking_id());
		PreparedStatement pstmtKeep = conn.prepareStatement("Select * from passenger where booking_id = ?;");
		pstmtKeep.setInt(1, fb.getBooking_id());
		ResultSet rs = pstmtKeep.executeQuery();
		while (rs.next()) {
			String sql2 = null;
			Integer temp = 0;
			if (rs.getString("seat_type").equals("F")) {
				temp = (f.getReserved_first() - 1);
				f.setReserved_first(temp);
				sql2 = "UPDATE flight SET reserved_first = ?  WHERE route_id=?";
			} else if (rs.getString("seat_type").equals("B")) {
				temp = (f.getReserved_bus() - 1);
				f.setReserved_bus(temp);
				sql2 = "UPDATE flight SET reserved_bus = ?  WHERE route_id=?";
			} else if (rs.getString("seat_type").equals("E")) {
				temp = (f.getReserved_econ() - 1);
				f.setReserved_econ(temp);
				sql2 = "UPDATE flight SET reserved_econ = ?  WHERE route_id=?";
			}
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, temp);
			pstmt2.setInt(2, f.getRoute_id());
			pstmt2.execute();
		}
		PreparedStatement pstmt = conn.prepareStatement("Delete from passenger where booking_id = ?;");
		pstmt.setInt(1, fb.getBooking_id());
		pstmt.execute();
		PreparedStatement pstmt5 = conn
				.prepareStatement("Delete from flight_bookings where flight_id= ? and booking_id=?");
		pstmt5.setInt(1, f.getId());
		pstmt5.setInt(2, fb.getBooking_id());
		pstmt5.execute();
		PreparedStatement pstmt7 = conn.prepareStatement("Delete from booking_user where booking_id= ? and user_id=?");
		pstmt7.setInt(1, fb.getBooking_id());
		pstmt7.setInt(2, u.getId());
		pstmt7.execute();
		PreparedStatement pstmt8 = conn.prepareStatement("Delete from booking where booking_id= ?");
		pstmt7.setInt(1, fb.getBooking_id());
		pstmt7.execute();
		conn.commit();
		System.out.println(fb.getBooking_id());
		TravelMenu.travelMenuOp(u);
	}

	public static void adminPrintBook() throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		Booking b = new Booking();
		PassengerDAO pd = new PassengerDAO();
		List<Object> bList = pd.getBookingAdminList();
		if (bList.size() > 0) {
			for (int i = 0; i < bList.size(); i++) {
				b = (Booking) bList.get(i);
				System.out.println("Booking Id: " + b.getId() + "\nCurrently Active: " + b.isIs_active()
						+ "\nConfirmation Code: " + b.getConfirmation_code());
			}
		}
	}

	public static void AdminMenuBookSeat(Employee e, User u) throws Exception {

		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		Booking_User bu = new Booking_User();
		PassengerDAO pd = new PassengerDAO();
		bu = pd.getBookingFromUser(u.getId());
		List<Object> buList = pd.getBookingAdminList();
		String sql = "select * from flight where route_id= ?";
		FlightDAO fDOA = new FlightDAO();
		Flight f = fDOA.getFlightData(sql, e.getRouteID());
		ResultSet rs;
		boolean checkExist = false;
		if (buList.size() > 0) {
			for (int i = 0; i < buList.size(); i++) {
				PreparedStatement pstmtCheck = conn
						.prepareStatement("select * from flight_bookings where flight_id=? and booking_id=?");
				pstmtCheck.setInt(1, f.getId());
				bu = (Booking_User) buList.get(i);
				pstmtCheck.setInt(2, bu.getBooking_id());
				rs = pstmtCheck.executeQuery();
				if (rs.next()) {
					PreparedStatement pstmt = conn.prepareStatement(
							"Insert into passenger values (null,?,'test','test','2000/01/01','M','null island','N');");
					pstmt.setInt(1, bu.getBooking_id());
					pstmt.execute();
					conn.commit();
					checkExist = true;
				}
			}
		}

		AirplaneType at = new AirplaneType();
		AirplaneDAO ad = new AirplaneDAO();

		at = ad.getAirplaneType((ad.getAirplane(f.getAirplane_id())).getType_id());

		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		Random gen = new Random();
		String testNum = Integer.toString(gen.nextInt(899999) + 100001);
		if (checkExist == false) {

			PreparedStatement pstmtBook = conn.prepareStatement("Insert into booking values (null,1,?);");
			pstmtBook.setString(1, testNum);
			pstmtBook.execute();
			conn.commit();
			Booking b = new Booking();
			b = pd.getBooking(testNum);
			PreparedStatement pstmt = conn.prepareStatement(
					"Insert into passenger values (null,?,'test','test','2000/01/01','M','null island','N');");

			pstmt.setInt(1, b.getId());
			pstmt.execute();
			PreparedStatement pstmt3 = conn.prepareStatement("Insert into booking_user values (?,?);");
			pstmt3.setInt(1, b.getId());
			pstmt3.setInt(2, u.getId());
			pstmt3.execute();
			PreparedStatement pstmt6 = conn
					.prepareStatement("select * from flight_bookings where flight_id=? and booking_id=?");
			pstmt6.setInt(1, f.getId());
			pstmt6.setInt(2, b.getId());

			ResultSet rs2 = pstmt6.executeQuery();

			if (!rs2.next()) {
				PreparedStatement pstmt5 = conn.prepareStatement(
						"Insert into flight_bookings values ((select id from flight where id=?),(select id from booking where id=?));");
				pstmt5.setInt(1, f.getId());
				pstmt5.setInt(2, b.getId());
				pstmt5.execute();
			}
		}
		List<String> placeW = new ArrayList<String>();
		List<String> placeComp = new ArrayList<String>();
		placeComp.add("First");
		placeComp.add("Business");
		placeComp.add("Economy");
		int i;
		if (firstPlace > 0) {
			placeW.add("First");

		}
		if (busPlace > 0) {
			placeW.add("Business");
		}
		if (econPlace > 0) {
			placeW.add("Economy");
		}
		EmployeeMenu.flightDetails(e);
		System.out.println("\n");
		TimeUnit.SECONDS.sleep(1);
		for (i = 0; i < placeW.size(); i++) {
			System.out.println((i + 1) + ") " + placeW.get(i));
		}

		System.out.println(i + 1 + ") Quit to previous");
		Integer categ = Driver.scanHandleInt();
		if (categ - 1 == i) {
			if (!checkExist) {
				conn.rollback();
				Booking b = new Booking();
				b = pd.getBooking(testNum);
				System.out.println(b.getId());
				PreparedStatement pstmtRoll = conn.prepareStatement("Delete from booking where id = ?;");
				pstmtRoll.setInt(1, b.getId());
				pstmtRoll.execute();
				conn.commit();
			}
			AdminMenu.adminMenu();
		} else if (categ <= placeW.size()) {
			System.out.println(i);
			for (int j = 0; j < placeW.size(); j++) {
				if (placeW.get(categ - 1).equals(placeComp.get(j))) {

					switch (placeW.get(categ - 1)) {
					case "First":
						System.out.println("You have booked a First Class seat.");
						f.setReserved_first((f.getReserved_first() + 1));
						PreparedStatement pstmt2 = conn
								.prepareStatement("UPDATE flight SET reserved_first = ?  WHERE route_id=?");
						pstmt2.setInt(1, f.getReserved_first());
						pstmt2.setInt(2, f.getRoute_id());
						pstmt2.execute();
						PreparedStatement pstmt3 = conn
								.prepareStatement("UPDATE passenger SET seat_type=?  WHERE seat_type=");
						pstmt3.setString(1, "F");
						pstmt3.setString(2, "N");
						pstmt3.execute();
						break;
					case "Business":
						System.out.println("You have booked a Business Class seat.");
						f.setReserved_bus((f.getReserved_bus() + 1));
						PreparedStatement pstmt9 = conn
								.prepareStatement("UPDATE flight SET reserved_bus = ?  WHERE route_id=?");
						pstmt9.setInt(1, f.getReserved_bus());
						pstmt9.setInt(2, f.getRoute_id());
						pstmt9.execute();
						f.getRoute_id();
						PreparedStatement pstmtS = conn
								.prepareStatement("UPDATE passenger SET seat_type=?  WHERE seat_type=");
						pstmtS.setString(1, "B");
						pstmtS.setString(2, "N");
						pstmtS.execute();
						break;
					case "Economy":
						f.setReserved_econ((f.getReserved_econ() + 1));
						System.out.println("You have booked an Economy Class seat.");
						PreparedStatement pstmt4 = conn
								.prepareStatement("UPDATE flight SET reserved_econ = ?  WHERE route_id=?");
						pstmt4.setInt(1, f.getReserved_econ());
						pstmt4.setInt(2, f.getRoute_id());
						pstmt4.execute();
						f.getRoute_id();
						PreparedStatement pstmt0 = conn
								.prepareStatement("UPDATE passenger SET seat_type=?  WHERE seat_type=");
						pstmt0.setString(1, "E");
						pstmt0.setString(2, "N");
						pstmt0.execute();
						break;
					}
				}
			}
			conn.commit();
			AdminMenu.adminMenu();
		}

	}

	public static void passengerPrint() throws Exception {
		PassengerDAO pd = new PassengerDAO();
		List<Object> pList = pd.getPassengerList();
		System.out.print("Passenger List");
		Passenger p = new Passenger();
		System.out.println(
				"\nPassenger Id  Booking ID  Given Name  Family Name       DOB       Gender       Address      Seat Type");
		if (pList.size() > 0) {
			for (int i = 0; i < pList.size(); i++) {
				p = (Passenger) pList.get(i);

				System.out.println(p.getId() + "               " + p.getBooking_id() + "         " + p.getGiven_name()
						+ "        " + p.getFamily_name() + "         " + p.getDob() + "    " + p.getGender()
						+ "         " + p.getAddress() + "      " + p.getSeat_type());

			}
		}
	}
}
