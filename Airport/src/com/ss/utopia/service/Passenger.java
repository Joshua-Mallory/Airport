package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Passenger {
	private Integer id;
	private Integer booking_id;
	private String given_name;
	private String family_name;
	private Date dob;
	private String gender;
	private String address;

	public Passenger() {
	}

	public Passenger(Integer id, Integer booking_id, String given_name, String family_name, Date dob, String gender,
			String address) {
		super();
		this.id = id;
		this.booking_id = booking_id;
		this.given_name = given_name;
		this.family_name = family_name;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
	}

	public Passenger getPassengerInfo(Integer book_id) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM passenger where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Passenger p = new Passenger(rs.getInt("id"), rs.getInt("booking_id"), rs.getString("given_name"),
				rs.getString("family_name"), rs.getDate("dob"), rs.getString("gender"), rs.getString("address"));
		return p;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(Integer booking_id) {
		this.booking_id = booking_id;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
