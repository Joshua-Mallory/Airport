package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AirplaneType {
	private Integer id;
	private Integer econ_capacity;
	private Integer bus_capacity;
	private Integer first_capacity;

	public AirplaneType() {

	}

	public AirplaneType(Integer id, Integer econ_capacity, Integer bus_capacity, Integer first_capacity) {
		this.id = id;
		this.econ_capacity = econ_capacity;
		this.bus_capacity = bus_capacity;
		this.first_capacity = first_capacity;
	}

	public AirplaneType getAirplane(Integer type_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from airplane_type where id= '" + type_id + "';");
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Integer id = rs.getInt("id");
		Integer econ_capacity = rs.getInt("econ_capacity");
		Integer bus_capacity = rs.getInt("bus_capacity");
		Integer first_capacity = rs.getInt("first_capacity");
		AirplaneType at = new AirplaneType(id, econ_capacity, bus_capacity, first_capacity);
		return at;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEcon_capacity() {
		return econ_capacity;
	}

	public void setEcon_capacity(Integer econ_capacity) {
		this.econ_capacity = econ_capacity;
	}

	public Integer getBus_capacity() {
		return bus_capacity;
	}

	public void setBus_capacity(Integer bus_capacity) {
		this.bus_capacity = bus_capacity;
	}

	public Integer getFirst_capacity() {
		return first_capacity;
	}

	public void setFirst_capacity(Integer first_capacity) {
		this.first_capacity = first_capacity;
	}

}
