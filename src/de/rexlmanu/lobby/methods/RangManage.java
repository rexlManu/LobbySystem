package de.rexlmanu.lobby.methods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.bukkit.entity.Player;

import de.rexlmanu.lobby.Main;

public class RangManage {

	public static HashMap<Player, Rangs> getRang = new HashMap<>();
	
	private Player p;
	private Rangs rang;
	
	public RangManage(Player p){
		if(!getRang.containsKey(p)){
			loadRang(p);
		}
		this.p = p;
		this.rang = getRang.get(this.p);
	}
	public Rangs getRang() {
		return rang;
	}
	
	public Rangs setRang(Rangs rang){
		getRang.remove(this.p);
		getRang.put(this.p, rang);
		this.rang = rang;
		try {
			setRang(trans(this.p.getUniqueId().toString()), rang);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.rang;
	}
	public boolean hasPermissions(Rangs rang){
		if(this.rang.getList(this.rang).contains(rang)){
			return true;
		}else{
			return false;
		}
	}
	
	// - = [ MYSQL ] = -
	
	public static void loadRang(Player p){
		try {
			getRang.put(p, getRangMySQL(trans(p.getUniqueId().toString())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Rangs getRangMySQL(String uuid) throws SQLException{
		PreparedStatement st;
		st = MySQL.con.prepareStatement("SELECT Rang FROM rangManage WHERE UUID = ?");
		st.setString(1, uuid);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			return getRangFromString(rs.getString("Rang"));
		}
		return Rangs.Spieler;
	}
	public static void setRang(String uuid,Rangs rang) throws SQLException{
		if(isExisting(trans(uuid))){
			PreparedStatement st = MySQL.con.prepareStatement("UPDATE rangManage SET Rang = ? WHERE UUID = ?");
			st.setString(1, rang.toString());
			st.setString(2, uuid);
			st.executeUpdate();
		}
	}
	public static boolean isExisting(String uuid){
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM rangManage WHERE UUID= '" + uuid + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			Main.mysql.update("INSERT INTO rangManage (UUID, Rang) VALUES ('" + uuid+ "', '"+Rangs.Spieler+"');");
			return false;
		} catch (SQLException e) {
		}
		Main.mysql.update("INSERT INTO rangManage (UUID, Rang) VALUES ('" + uuid+ "', '"+Rangs.Spieler+"');");
		return false;
	}
	public static String trans(String uuid){
		return uuid.replace("-", "");
	}
	public static Rangs getRangFromString(String rang){
		for(Rangs rangs:Rangs.values()){
			if(rangs.toString().equalsIgnoreCase(rang)){
				return rangs;
			}
		}
		return Rangs.Spieler;
	}
	
}
