package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Booking_User {

	private Integer user_id;
	private Integer booking_id;

	public Booking_User() {
	}

	public Booking_User(Integer user_id, Integer booking_id) {
		this.user_id = user_id;
		this.booking_id = booking_id;
	}

	public Booking_User getBookingUser(Integer book_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking_user where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Booking_User bu = new Booking_User(rs.getInt("user_id"), rs.getInt("booking_id"));
		return bu;

	}

	public List<Object> getBookingUserList(Integer book_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		List<Object> books = new ArrayList<Object>();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking_user where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Booking_User bu = new Booking_User(rs.getInt("user_id"), rs.getInt("booking_id"));
			books.add(bu);
		}
		return books;

	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(Integer booking_id) {
		this.booking_id = booking_id;
	}

}
