package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Traveler {

	public Traveler() {
	}

	public boolean checkUserCred(String user, String pass) throws Exception {
		ConnectSetup cs = new ConnectSetup();
		Connection conn = cs.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user USER username = '" + user + "';");
		// pstmt.setString(1, user);
		// pstmt.setString(2, pass);
		ResultSet rs = pstmt.executeQuery();

		return rs.next();
	}

}
