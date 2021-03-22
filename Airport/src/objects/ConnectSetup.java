package objects;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSetup {

	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/utopia2";
	private final String username = "root";
	private final String password = "Seventhjob7!";
	private static ConnectSetup conn = null;

	public Connection getConnection() throws Exception {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		return conn;

	}

}
