package de.rexlmanu.lobby.methods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.rexlmanu.lobby.Main;

public class OfflineSupport {

	public static String getName(String uuid) throws SQLException{
		PreparedStatement st;
		st = MySQL.con.prepareStatement("SELECT NAME FROM offlineSupport WHERE UUID = ?");
		st.setString(1, trans(uuid));
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			return rs.getString("NAME");
		}
		return "ERROR";
	}
	public static String getUUID(String name) throws SQLException{
		PreparedStatement st;
		st = MySQL.con.prepareStatement("SELECT UUID FROM offlineSupport WHERE NAME = ?");
		st.setString(1, name);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			return rs.getString("UUID");
		}
		return "ERROR";
	}
	public static void update(String name,String uuid) throws SQLException{
			PreparedStatement st = MySQL.con.prepareStatement("UPDATE offlineSupport SET NAME = ? WHERE UUID = ?");
			st.setString(1, name);
			st.setString(2, trans(uuid));
			st.executeUpdate();
	}
	
	public static boolean isExisting(String uuid, String name){
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM offlineSupport WHERE UUID= '" + trans(uuid) + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			Main.mysql.update("INSERT INTO offlineSupport (UUID, NAME) VALUES ('" + trans(uuid)+ "', '"+name+"');");
			return false;
		} catch (SQLException e) {
		}
		Main.mysql.update("INSERT INTO offlineSupport (UUID, NAME) VALUES ('" + trans(uuid)+ "', '"+name+"');");
		return false;
	}
	private static String trans(String uuid){
		return uuid.replace("-", "");
	}
}
