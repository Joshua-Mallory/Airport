package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Flight_Bookings {
	private Integer flight_id;
	private Integer booking_id;

	public Flight_Bookings() {
		// TODO Auto-generated constructor stub
	}

	public Flight_Bookings(Integer flight_id, Integer booking_id) {
		this.flight_id = flight_id;
		this.booking_id = booking_id;
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

	public List<Object> getfbList(Integer book_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		List<Object> books = new ArrayList<Object>();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM flight_bookings where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Flight_Bookings fb = new Flight_Bookings(rs.getInt("flight_id"), rs.getInt("booking_id"));
			books.add(fb);
		}
		return books;

	}

	public Integer getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(Integer flight_id) {
		this.flight_id = flight_id;
	}

	public Integer getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(Integer booking_id) {
		this.booking_id = booking_id;
	}

}
