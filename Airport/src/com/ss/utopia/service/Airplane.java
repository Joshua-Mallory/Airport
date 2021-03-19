package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Airplane {

	private Integer id;
	private Integer type_id;

	public Airplane() {
	};

	public Airplane(Integer id, Integer type_id) {
		this.id = id;
		this.type_id = type_id;
	}

	public Airplane getAirplane(Integer airplane_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from airplane where id= '" + airplane_id + "';");
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Integer id = rs.getInt("id");
		Integer type_id = rs.getInt("type_id");
		Airplane a = new Airplane(id, type_id);
		return a;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

}
