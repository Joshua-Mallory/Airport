package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import objects.Airplane;
import objects.AirplaneType;
import objects.ConnectSetup;

public class AirplaneDAO {

	public AirplaneDAO() {
		// TODO Auto-generated constructor stub
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

	public AirplaneType getAirplaneType(Integer type_id) throws Exception {
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
}
