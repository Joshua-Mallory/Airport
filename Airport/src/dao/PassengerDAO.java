package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objects.Booking;
import objects.Booking_User;
import objects.ConnectSetup;
import objects.Passenger;
import objects.User;

public class PassengerDAO {

	public Connection conSet() {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = null;
		try {
			conn = cs.getConnection();
		} catch (Exception e) {
			System.out.println("Bad Connection");
			e.printStackTrace();
		}
		return conn;
	}

	public User getUserCred(String user, String pass) throws Exception {
		ResultSet rs = commonCheck(user, pass);
		rs.next();
		User u = new User(rs.getInt("id"), rs.getInt("role_id"), rs.getString("given_name"),
				rs.getString("family_name"), rs.getString("username"), rs.getString("email"), rs.getString("password"),
				rs.getString("phone"));
		return u;
	}

	public boolean checkUserCred(String user, String pass) throws Exception {
		ResultSet rs = commonCheck(user, pass);
		return rs.next();
	}

	public ResultSet commonCheck(String user, String pass) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user where username = ? and password = ?");
		pstmt.setString(1, user);
		pstmt.setString(2, pass);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public Passenger getPassengerInfo(Integer book_id) throws Exception {
		Connection conn = conSet();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM passenger where booking_id= ? ");
		pstmt.setInt(1, book_id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Passenger p = new Passenger(rs.getInt("id"), rs.getInt("booking_id"), rs.getString("given_name"),
				rs.getString("family_name"), rs.getDate("dob"), rs.getString("gender"), rs.getString("address"),
				rs.getString("seat_type"));
		return p;

	}

	public ResultSet bookingCommon(Integer user_id) throws SQLException {
		Connection conn = conSet();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM booking_user where user_id= ? ");
		pstmt.setInt(1, user_id);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public Booking_User getBookingFromUser(Integer user_id) throws Exception {
		ResultSet rs = bookingCommon(user_id);
		Booking_User bu = new Booking_User();
		while (rs.next()) {
			bu = new Booking_User(rs.getInt("booking_id"), rs.getInt("user_id"));
		}
		return bu;
	}

	public List<Object> getBookingUserList(Integer user_id) throws Exception {
		ResultSet rs = bookingCommon(user_id);
		List<Object> books = new ArrayList<Object>();
		while (rs.next()) {
			Booking_User bu = new Booking_User(rs.getInt("booking_id"), rs.getInt("user_id"));
			System.out.println(bu.getBooking_id());
			books.add(bu);
		}
		return books;
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

}
