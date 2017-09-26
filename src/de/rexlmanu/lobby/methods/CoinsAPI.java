package de.rexlmanu.lobby.methods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.rexlmanu.lobby.Main;

public class CoinsAPI {
	
	public static void setCoins(String uuid,int coins) throws SQLException{
		PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinsAPI SET Coins = ? WHERE UUID = ?");
		st.setInt(1, coins);
		st.setString(2, trans(uuid));
		st.executeUpdate();
	}
	public static void removeCoins(String uuid,int coins){
		try {
			setCoins(uuid, getCoins(uuid) - coins);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void addCoins(String uuid,int coins){
		try {
			setCoins(uuid, getCoins(uuid) + coins);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int getCoins(String uuid) throws SQLException{
		PreparedStatement st;
		st = MySQL.con.prepareStatement("SELECT Coins FROM coinsAPI WHERE UUID = ?");
		st.setString(1, trans(uuid));
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			return rs.getInt("Coins");
		}
		return 0;
	}
	public static boolean isExisting(String uuid){
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM coinsAPI WHERE UUID= '" + trans(uuid) + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			Main.mysql.update("INSERT INTO coinsAPI (UUID, Coins) VALUES ('" + trans(uuid)+ "', '0');");
			return false;
		} catch (SQLException e) {
		}
		Main.mysql.update("INSERT INTO coinsAPI (UUID, Coins) VALUES ('" + trans(uuid)+ "', '0');");
		return false;
	}
	private static String trans(String uuid){
		return uuid.replace("-", "");
	}

}
