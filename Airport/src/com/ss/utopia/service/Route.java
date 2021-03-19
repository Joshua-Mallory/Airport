package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Route {
	private Integer id;
	private String origin;
	private String destination;

	public Route() {
	}

	public Route(Integer id, String origin, String destination) {
		this.id = id;
		this.origin = origin;
		this.destination = destination;
	}

	public List<Object> getEmployeeRoute() throws Exception {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
