package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.service.Driver;

import objects.ConnectSetup;
import objects.Employee;
import objects.Route;

public class RouteDAO {

	public Employee getEmployee(String orig, String dest) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from route where origin_id = ? and destination_id=?");
		pstmt.setString(1, orig);
		pstmt.setString(2, dest);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Integer routeID = rs.getInt("id");
		Route r = new Route(routeID, rs.getString("origin_id"), rs.getString("destination_id"));
		PreparedStatement pstmt2 = conn.prepareStatement("select * from airport; ");
		ResultSet rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + r.getOrigin() + "';");
		rs2.next();
		String iata_id = rs2.getString("iata_id");
		String cityid = rs2.getString("city");
		rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + r.getDestination() + "';");
		rs2.next();
		Employee e = new Employee(routeID, iata_id, cityid, rs2.getString("iata_id"), rs2.getString("city"));
		return e;
	}

	public List<Object> getEmployeeRouteList() throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from route; ");
		ResultSet rs = pstmt.executeQuery();
		List<Object> emplFlight = new ArrayList<Object>();
		while (rs.next()) {
			Integer routeID = rs.getInt("id");
			Route r = new Route(routeID, rs.getString("origin_id"), rs.getString("destination_id"));
			PreparedStatement pstmt2 = conn.prepareStatement("select * from airport; ");
			ResultSet rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + r.getOrigin() + "';");
			rs2.next();
			String iata_id = rs2.getString("iata_id");
			String cityid = rs2.getString("city");
			rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + r.getDestination() + "';");
			rs2.next();
			Employee c = new Employee(routeID, iata_id, cityid, rs2.getString("iata_id"), rs2.getString("city"));
			emplFlight.add(c);
		}
		return emplFlight;
	}

	public Employee getCustomerRoute(Integer id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from route where id =? ;");
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Integer routeID = rs.getInt("id");
		Route r = new Route(routeID, rs.getString("origin_id"), rs.getString("destination_id"));
		PreparedStatement pstmt2 = conn.prepareStatement("select * from airport; ");
		ResultSet rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + r.getOrigin() + "';");
		rs2.next();
		String iata_id = rs2.getString("iata_id");
		String cityid = rs2.getString("city");
		rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + r.getDestination() + "';");
		rs2.next();
		Employee c = new Employee(routeID, iata_id, cityid, rs2.getString("iata_id"), rs2.getString("city"));

		return c;
	}

	public boolean employeeRouteCheck(String air, String city) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt2 = conn
				.prepareStatement("select * from airport where iata_id= '" + air + "'and city= '" + city + "';");
		ResultSet rs2 = pstmt2.executeQuery();
		return rs2.next();
	}

	public static boolean airportAddTest(String test) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from airport where iata_id= ? ;");
		pstmt.setString(1, test);
		ResultSet rs2 = pstmt.executeQuery();
		return rs2.next();
	}

	public static void airportAdd() throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		FlightDAO fd = new FlightDAO();
		RouteDAO rd = new RouteDAO();
		System.out.println("What is the three letter Airport Origin?");
		String orig = Driver.scanHandleString();
		while (airportAddTest(orig)) {
			System.out.println("Already in the list. Name another three letter Airport Origin.");
			orig = Driver.scanHandleString();
		}
		System.out.println("What is the airport city name?");
		String city = Driver.scanHandleString();
		PreparedStatement pstmt = conn.prepareStatement("insert into airport values('?', '?')");
		pstmt.setString(1, orig);
		pstmt.setString(2, city);
		pstmt.execute();
		conn.commit();
	}

	public static void airportDisplay() throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from airport;");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("iata_id") + "   " + rs.getString("city"));
		}
	}
}
