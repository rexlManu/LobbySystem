package de.rexlmanu.lobby.methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.rexlmanu.lobby.Main;

public class MySQL {
	private String HOST = "";
	private String DATABASE = "";
	private String USER = "";
	private String PASSWORD = "";

	public static int Connection = 0;
	public static Connection con;

	public MySQL(String host, String database, String user, String password) {
		this.HOST = host;
		this.DATABASE = database;
		this.USER = user;
		this.PASSWORD = password;

		connect();
	}

	public void connect() {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", this.USER,
					this.PASSWORD);
			System.out.println("[MySQL] Verbindung hergestellt!");
			Connection = 1;
		} catch (SQLException e) {
			System.out.println("[MySQL] Verbindung konnte nicht hergestellt werden! Fehler: " + e.getMessage());
			Connection = 0;
		}
	}

	public void close() {
		try {
			if (con != null) {
				con.close();
				System.out.println("[MySQL] Verbindung wurde beendet.");
				Connection = 2;
			}
		} catch (SQLException e) {
			System.out.println("[MySQL] Verbindung konnte nicht beendet werden! Fehler: " + e.getMessage());
			Connection = 3;
		}
	}

	public void update(String qry) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate(qry);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public static void createTable() {
		try {
			Statement st = con.createStatement();
			st.executeUpdate("CREATE TABLE IF NOT EXISTS rangManage(UUID varchar(100),Rang varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS offlineSupport(UUID varchar(100),NAME varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS coinsAPI(UUID varchar(100),Coins varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS settingsAPI(UUID varchar(100),Einstellung varchar(100),Wert varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS LBplayerhider(UUID varchar(100),Wert varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS spurenExtras(UUID varchar(100),Spuren varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS bootsExtras(UUID varchar(100),Boots varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS gadgetsExtras(UUID varchar(100),Gadgets varchar(100))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS headsExtras(UUID varchar(100),Heads varchar(100))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}

		return rs;
	}

	public static void Update(String qry) {
		try {
			@SuppressWarnings("static-access")
			Statement stmt = Main.mysql.con.createStatement();
			stmt.executeUpdate(qry);
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public ResultSet getResult(String qry) {
		ResultSet rs = null;
		
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return rs;
	}
}
