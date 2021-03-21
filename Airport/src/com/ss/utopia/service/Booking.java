package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Booking {
	private Integer id;
	private boolean is_active;
	private String confirmation_code;

	public Booking() {
	}

	public Booking(Integer id, boolean is_active, String confirmation_code) {
		super();
		this.id = id;
		this.is_active = is_active;
		this.confirmation_code = confirmation_code;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getConfirmation_code() {
		return confirmation_code;
	}

	public void setConfirmation_code(String confirmation_code) {
		this.confirmation_code = confirmation_code;
	}

}