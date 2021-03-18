package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConnectSetup {

	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/utopia";
	private final String username = "root";
	private final String password = "Seventhjob7!";
	private static ConnectSetup conn = null;

	public List<Object> getConnection() throws Exception {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("select * from route; ");
		// PreparedStatement pstmt3 = conn.prepareStatement("select * from airport; ");
		// ResultSet rs3 = pstmt3.executeQuery();
		// Scanner scan = new Scanner(System.in);
		// airportCode = scan.nextLine();
		// pstmt.setString(1, airportCode);
		ResultSet rs = pstmt.executeQuery();
		// System.out.println(rs.getFetchSize());
		Integer counter = 0;

		List<Object> emplFlight = new ArrayList<Object>();

		while (rs.next()) {
			counter++;
			Integer id = rs.getInt("id");
			String origin = rs.getString("origin_id");
			String destination = rs.getString("destination_id");
			PreparedStatement pstmt2 = conn.prepareStatement("select * from airport; ");
			ResultSet rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + origin + "';");
			rs2.next();
			String routeid = rs2.getString("iata_id");
			String cityid = rs2.getString("city");
			rs2 = pstmt2.executeQuery("select * from airport where iata_id= '" + destination + "';");
			rs2.next();
			String routeid2 = rs2.getString("iata_id");
			String cityid2 = rs2.getString("city");
			// System.out.println(counter + ") " + routeid + "," + cityid + "->" + routeid2
			// + "," + cityid2);
			Object[] temp = { routeid, cityid, routeid2, cityid2 };
			emplFlight.add(temp);
			// System.out.print(Arrays.toString(temp));
		}
		return emplFlight;

	}

	public void returnValue() {
	}

}
