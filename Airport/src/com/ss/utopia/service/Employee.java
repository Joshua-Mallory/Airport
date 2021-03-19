package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Employee {
	private String originID;
	private String originName;
	private String destID;
	private String destName;
	private Integer routeID;

	public Employee(Integer routeID, String originID, String originName, String destID, String destName) {
		this.routeID = routeID;
		this.originID = originID;
		this.originName = originName;
		this.destID = destID;
		this.destName = destName;
	}

	public String empRoutePrint() {

		return getOriginID() + ", " + getOriginName() + " -> " + getDestID() + ", " + getDestName();
	}

	public Employee getEmployee(Integer id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from route where id = '" + id + "';");
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

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public String getOriginID() {
		return originID;
	}

	public void setOriginID(String originID) {
		this.originID = originID;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getDestID() {
		return destID;
	}

	public void setDestID(String destID) {
		this.destID = destID;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

}
