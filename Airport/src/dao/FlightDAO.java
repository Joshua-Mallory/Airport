package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ss.utopia.service.Driver;

import objects.AirplaneType;
import objects.ConnectSetup;
import objects.Employee;
import objects.Flight;
import objects.Flight_Bookings;

public class FlightDAO {

	public FlightDAO() {
		// TODO Auto-generated constructor stub
	}

	public Flight getFlightData(String sql, Integer inID) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, inID);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return flightOverlap(rs);
	}

	public List<Object> getFlightDataList(String sql, Integer inID) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, inID);
		ResultSet rs = pstmt.executeQuery();
		List<Object> flights = new ArrayList<Object>();
		while (rs.next()) {

			flights.add(flightOverlap(rs));
		}
		return flights;
	}

	public Flight_Bookings getFlightbook(Integer book_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM flight_bookings where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Flight_Bookings fb = new Flight_Bookings(rs.getInt("flight_id"), rs.getInt("booking_id"));
		return fb;
	}

	public List<Object> getFlightbookList(Integer book_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM flight_bookings where flight_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		List<Object> fbl = new ArrayList<Object>();
		while (rs.next()) {
			Flight_Bookings fb = new Flight_Bookings(rs.getInt("flight_id"), rs.getInt("booking_id"));
			fbl.add(fb);
		}
		return fbl;
	}

	public Flight flightOverlap(ResultSet rs) throws SQLException {
		Integer id = rs.getInt("id");
		Integer route_id = rs.getInt("route_id");
		Integer airplane_id = rs.getInt("airplane_id");
		Date departure_time_date = rs.getDate("departure_time");
		Time departure_time_time = rs.getTime("departure_time");
		Date arrival_time_date = rs.getDate("arrival_time");
		Time arrival_time_time = rs.getTime("arrival_time");
		Integer reserved_econ = rs.getInt("reserved_econ");
		Integer reserved_bus = rs.getInt("reserved_bus");
		Integer reserved_first = rs.getInt("reserved_first");
		float econ_price = rs.getFloat("econ_price");
		float bus_price = rs.getFloat("bus_price");
		float first_price = rs.getFloat("first_price");
		Flight f = new Flight(id, route_id, airplane_id, departure_time_date, departure_time_time, arrival_time_date,
				arrival_time_time, reserved_econ, reserved_bus, reserved_first, econ_price, bus_price, first_price);
		return f;
	}

	public List<Object> adminFlightList() throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM flight;");
		ResultSet rs = pstmt.executeQuery();
		List<Object> fList = new ArrayList<Object>();
		while (rs.next()) {
			Flight f = flightOverlap(rs);
			fList.add(f);
		}
		return fList;
	}

	public void flightUpdate(Employee e) throws Exception {
		FlightDAO fDOA = new FlightDAO();
		String sql = "select * from flight where route_id= ?";
		Flight f = fDOA.getFlightData(sql, e.getRouteID());
		RouteDAO rd = new RouteDAO();
		System.out.println("Please enter new Origin Airport and City or enter N/A for no change:");
		String origin = Driver.scanCheckNA();
		String holdAir = null;
		String holdCity = null;
		String[] orgAirCity = null;
		if (!origin.equals("N/A")) {
			orgAirCity = Driver.scanHandleString(origin);
			while (!rd.employeeRouteCheck(orgAirCity[0], orgAirCity[1]) && !origin.equals("N/A")) {
				System.out
						.println("Invalid Input: Please enter new Origin Airport and City or enter N/A for no change:");
				origin = Driver.scanCheckNA();
				if (!origin.equals("N/A"))
					orgAirCity = Driver.scanHandleString(origin);
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
		String dest = Driver.scanCheckNA();
		String[] desAirCity = null;
		if (!dest.equals("N/A")) {
			desAirCity = Driver.scanHandleString(dest);
			while (!rd.employeeRouteCheck(desAirCity[0], desAirCity[1]) && !dest.equals("N/A")) {
				System.out.println(
						"Invalid Input: Please enter new Destination Airport and City or enter N/A for no change:");
				dest = Driver.scanCheckNA();
				if (!dest.equals("N/A"))
					desAirCity = Driver.scanHandleString(dest);
			}
			destAir = desAirCity[0];
			destCity = desAirCity[1];
		}
		if (dest.equals("N/A")) {
			destAir = e.getDestID();
			destCity = e.getDestName();
		}
		System.out.println("Please enter new Departure Date or enter N/A for no change:");
		String depD = Driver.scanCheckNA();
		String depDate = null;
		if (!depD.equals("N/A")) {
			depDate = Driver.scanHandleDate(depD);
		}
		System.out.println(depDate);
		if (depDate == null)
			depDate = String.valueOf(f.getDeparture_time_date());
		System.out.println("Please enter new Arrivale Date or enter N/A for no change:");
		String arrD = Driver.scanCheckNA();
		String arrDate = null;
		if (!arrD.equals("N/A")) {
			arrDate = Driver.scanHandleDate(arrD);
		}
		if (arrDate == null)
			arrDate = String.valueOf(f.getArrival_time_date());
		System.out.println("Please enter new Departure Time or enter N/A for no change:");
		String dept = Driver.scanCheckNA();
		Time depTime = null;
		if (!dept.equals("N/A")) {
			depTime = Driver.scanHandleTime(dept);
		}
		if (depTime == null)
			depTime = f.getDeparture_time_time();
		System.out.println("Please enter new Arrival Time or enter N/A for no change:");
		String arrT = Driver.scanCheckNA();
		Time arrTime = null;
		if (!arrT.equals("N/A")) {
			arrTime = Driver.scanHandleTime(arrT);
		}
		if (arrTime == null)
			arrTime = f.getArrival_time_time();
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		e = new Employee(e.getRouteID(), holdAir, holdCity, destAir, destCity);
		System.out.print(e.empRoutePrint());
		String depDateTime = depDate + " " + String.valueOf(depTime);
		String arrDateTime = arrDate + " " + String.valueOf(arrTime);
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
	}

	public void insertFlightDetails(Employee e, Flight f) throws Exception {
		System.out.println("Please enter a Departure Date.");
		String depD = Driver.scanCheckNA();
		String depDate = Driver.scanHandleDate(depD);
		System.out.println("Please enter an Arrivale Date.");
		String arrD = Driver.scanCheckNA();
		String arrDate = Driver.scanHandleDate(arrD);
		System.out.println("Please enter a Departure Time.");
		String dept = Driver.scanCheckNA();
		Time depTime = Driver.scanHandleTime(dept);
		System.out.println("Please enter an Arrival Time.");
		String arrT = Driver.scanCheckNA();
		Time arrTime = Driver.scanHandleTime(arrT);
		System.out.println("Please enter how many Economy Class seats are reserved.");
		Integer ecr = Driver.scanHandleInt();
		System.out.println("Please enter how many Business Class seats are reserved.");
		Integer bcr = Driver.scanHandleInt();
		System.out.println("Please enter how many First Class seats are reserved.");
		Integer fcr = Driver.scanHandleInt();
		System.out.println("Please enter Economy Class Price.");
		Integer ecrp = Driver.scanHandleInt();
		System.out.println("Please enter Business Class Price.");
		Integer bcrp = Driver.scanHandleInt();
		System.out.println("Please enter First Class Price.");
		Integer fcrp = Driver.scanHandleInt();

		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		String depDateTime = depDate + " " + String.valueOf(depTime);
		String arrDateTime = arrDate + " " + String.valueOf(arrTime);
		java.sql.Timestamp depDT = java.sql.Timestamp.valueOf(depDateTime);
		java.sql.Timestamp arrDT = java.sql.Timestamp.valueOf(arrDateTime);
		PreparedStatement pstmt2 = conn.prepareStatement(
				"UPDATE flight SET departure_time = ?, arrival_time = ?, reserved_econ=?,reserved_bus=?,reserved_first=?,econ_price=?,bus_price=?,first_price=? WHERE route_id=?");
		pstmt2.setTimestamp(1, depDT);
		pstmt2.setTimestamp(2, arrDT);
		pstmt2.setInt(3, ecr);
		pstmt2.setInt(4, bcr);
		pstmt2.setInt(5, fcr);
		pstmt2.setInt(6, ecrp);
		pstmt2.setInt(7, bcrp);
		pstmt2.setInt(8, fcrp);
		pstmt2.setInt(9, e.getRouteID());
		pstmt2.execute();
		conn.commit();

	}

	public Integer seatsAdd(Employee e, boolean permission) throws Exception {
		FlightDAO fDOA = new FlightDAO();
		String sql = "select * from flight where route_id= ?";
		Flight f = fDOA.getFlightData(sql, e.getRouteID());
		AirplaneDAO ad = new AirplaneDAO();
		AirplaneType at = new AirplaneType();
		at = ad.getAirplaneType((ad.getAirplane(f.getAirplane_id())).getType_id());

		Integer firstPlace = at.getFirst_capacity() - f.getReserved_first();
		Integer busPlace = at.getBus_capacity() - f.getReserved_bus();
		Integer econPlace = at.getEcon_capacity() - f.getReserved_econ();
		System.out.println("Existing number of First Class seats: " + firstPlace + ".");
		System.out.println("Existing number of Business Class seats: " + busPlace + ".");
		System.out.println("Existing number of Economy Class seats: " + econPlace + ".");
		System.out.println(
				"Pick the Seat Class you want to modify seats of, to your flight:\n1) First\n2) Business\n3) Economy\n4) Quit to cancel operation");

		Integer categ = Driver.scanHandleInt();
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		switch (categ) {
		case 1:
			System.out.println("Existing number of First Class seats: " + firstPlace + ".");
			if (!permission) {
				System.out.println("How many would you like to reserve");
			}
			boolean add = true;
			if (permission) {
				System.out.println("Would you like to\n1) Add seats\n2) Remove seats");
				Integer temp2 = Driver.scanHandleInt();
				switch (temp2) {
				case 1:
					System.out.println("How many seats would you like to reserve");
					break;
				case 2:
					System.out.println("How many seats would you like to remove");
					add = false;
					break;
				}
			}
			Integer temp = Driver.scanHandleInt();
			if (add)
				f.setReserved_first(f.getReserved_first() + temp);
			else if (!add)
				f.setReserved_first(f.getReserved_first() - temp);
			PreparedStatement pstmt2 = conn.prepareStatement("UPDATE flight SET reserved_first = ?  WHERE route_id=?");
			pstmt2.setInt(1, f.getReserved_first());
			pstmt2.setInt(2, f.getRoute_id());
			pstmt2.execute();
			break;
		case 2:
			System.out.println("Existing number of Business Class seats: " + busPlace + ".");
			if (!permission) {
				System.out.println("How many would you like to reserve");
			}
			boolean add2 = true;
			if (permission) {
				System.out.println("Would you like to\n1) Add seats\n2) Remove seats?");
				Integer temp2 = Driver.scanHandleInt();
				switch (temp2) {
				case 1:
					System.out.println("How many seats would you like to reserve");
					break;
				case 2:
					System.out.println("How many seats would you like to remove");
					add2 = false;
					break;
				}
			}

			Integer temp2 = Driver.scanHandleInt();
			if (add2)
				f.setReserved_bus(f.getReserved_bus() + temp2);
			else if (!add2)
				f.setReserved_bus(f.getReserved_bus() - temp2);
			PreparedStatement pstmt3 = conn.prepareStatement("UPDATE flight SET reserved_bus = ?  WHERE route_id=?");
			pstmt3.setInt(1, f.getReserved_bus());
			pstmt3.setInt(2, f.getRoute_id());
			pstmt3.execute();
			break;
		case 3:
			System.out.println("Existing number of Economy Class seats: " + econPlace + ".");
			if (!permission) {
				System.out.println("How many would you like to reserve");
			}
			boolean add3 = true;
			if (permission) {
				System.out.println("Would you like to\n1) Add seats\n2) Remove seats?");
				Integer temp3 = Driver.scanHandleInt();
				switch (temp3) {
				case 1:
					System.out.println("How many seats would you like to reserve");
					break;
				case 2:
					System.out.println("How many seats would you like to remove");
					add3 = false;
					break;
				}
			}
			Integer temp3 = Driver.scanHandleInt();
			if (add3)
				f.setReserved_econ(f.getReserved_econ() + temp3);
			else if (!add3)
				f.setReserved_econ(f.getReserved_econ() - temp3);
			PreparedStatement pstmt4 = conn.prepareStatement("UPDATE flight SET reserved_econ = ?  WHERE route_id=?");
			pstmt4.setInt(1, f.getReserved_econ());
			pstmt4.setInt(2, f.getRoute_id());
			pstmt4.execute();
			break;
		case 4:
			conn.commit();
			return categ;
		}
		conn.commit();
		firstPlace = at.getFirst_capacity() - f.getReserved_first();
		busPlace = at.getBus_capacity() - f.getReserved_bus();
		econPlace = at.getEcon_capacity() - f.getReserved_econ();
		System.out.println("\nUpdated Seat Record");
		System.out.println("Existing number of First Class seats: " + firstPlace + ".");
		System.out.println("Existing number of Business Class seats: " + busPlace + ".");
		System.out.println("Existing number of Economy Class seats: " + econPlace + ".");
		return categ;
	}

	public void insertFlight(Integer airType, String orig, String dest) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("Insert into route values(null,?,?)");
		pstmt.setString(1, orig);
		pstmt.setString(2, dest);
		pstmt.execute();
		conn.commit();
		RouteDAO rd = new RouteDAO();
		Employee e = rd.getEmployee(orig, dest);
		PreparedStatement pstmt2 = conn.prepareStatement(
				"insert into flight values (null,(select id from route where id =?),(select id from airplane where id = ?),'2021-01-31 12:00:00','2021-01-31 12:00:00',30,35,40,75,100,150)");
		pstmt2.setInt(1, e.getRouteID());
		pstmt2.setInt(2, airType);
		pstmt2.execute();
		conn.commit();
		String sql = "select * from flight where route_id= ?";
		insertFlightDetails(e, getFlightData(sql, e.getRouteID()));
	}

	public void deleteFlight(Employee e) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		FlightDAO fd = new FlightDAO();
		Integer id = e.getRouteID();
		Flight f = fd.getFlightData("select * from flight where route_id= ?", id);
		List<Object> fbl = fd.getFlightbookList(f.getId());
		Flight_Bookings fb = new Flight_Bookings();
		PreparedStatement pstmt = conn.prepareStatement("Delete from route where id =?");
		pstmt.setInt(1, id);
		pstmt.execute();
		PreparedStatement pstmt2 = conn.prepareStatement("Delete from flight where route_id =?");
		pstmt2.setInt(1, id);
		pstmt2.execute();
		PreparedStatement pstmt3 = conn.prepareStatement("Delete from flight_bookings where flight_id =?");
		pstmt3.setInt(1, f.getId());
		pstmt3.execute();
		for (int i = 0; i < fbl.size(); i++) {
			fb = (Flight_Bookings) fbl.get(i);
			PreparedStatement pstmt4 = conn.prepareStatement("Delete from booking where id =?");
			pstmt4.setInt(1, fb.getBooking_id());
			pstmt4.execute();
			PreparedStatement pstmt5 = conn.prepareStatement("Delete from passenger where booking_id =?");
			pstmt5.setInt(1, fb.getBooking_id());
			pstmt5.execute();
		}
		conn.commit();
	}

}
