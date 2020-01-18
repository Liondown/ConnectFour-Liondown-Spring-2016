import java.sql.*;

public class Database {

	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	String sql;
	String username;
	String password;

	public Database() {
		conn = null;
		statement = null;
		rs = null;
		sql = "";
		username = "root";
		password = "beerfordrink";
	}

	public void start() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/connectfour?useSSL=false", username,
					password);
			statement = conn.createStatement();
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS ConnectFour");
			sql = "CREATE TABLE IF NOT EXISTS info (" + "location TEXT NOT NULL, " + "token TEXT NOT NULL, "
					+ "time TEXT NOT NULL, " + "gameNumber TEXT NOT NULL)";
			statement.executeUpdate(sql);
			// Do something with the Connection
		} catch (SQLException se) {
			System.out.println("Exception: " + se.getMessage());
			// handle any errors
		}
	}

	public void writeResultSet(String text, int gameNumber, String time) {
		// R: 7 C: 8 . Player Jones's turn, Token O , Turn 1 . Time: 00:00:01
		String[] parts = text.split("\\ ");
		if (parts[0].equals("R:")) {
			String location = "R:" + parts[1] + " C:" + parts[3] + "";
			String player = parts[6];
			String token = parts[9];
			String tp = "Token: "+ token + " player: " + player;
		
			try {
				String sql ="INSERT INTO info (location, token, time, gameNumber) " + "VALUES ('" + location + "','" + tp + "','"
				           + time + "','" + gameNumber + "')";
				statement.executeUpdate(sql);
			} catch (SQLException se) {
				System.out.println("Exception: " + se.getMessage());
			}
		} else {
			try {
				String sql = "INSERT INTO info (location, token, time, gameNumber) " + "VALUES ('" + text + "', 'empty', '" + time + "', '"
					      + gameNumber + "')";
				statement.executeUpdate(sql);
			} catch (SQLException se) {
				System.out.println("Exception: " + se.getMessage());
			}
		}
	}

	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			System.out.println("Exception: " + se.getMessage());
		}
	}
}
