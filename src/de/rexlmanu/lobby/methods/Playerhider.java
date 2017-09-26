package de.rexlmanu.lobby.methods;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.freundesystem.Methods;
import de.rexlmanu.lobby.util.MessageAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Playerhider {

	public static HashMap<Player, Hider> playerHiderSettings = new HashMap<>();
	public static HashMap<Player, List<String>> playerFriends = new HashMap<>();
	public static enum Hider {
		All, Friends, Nothing
	}

	// 1 = ALL , 2 = Friends, 3 = Nothing

	public static Hider getHiderSettings(String uuid) {

		if (getInt(uuid) == 1) {
			return Hider.All;
		} else if (getInt(uuid) == 2) {
			return Hider.Friends;
		} else {
			return Hider.Nothing;
		}

	}
	
	public static void giveItem(Player p){
		if(playerHiderSettings.get(p) == Hider.All){
			p.getInventory().setItem(2, create("§8➟ §6Playerhider §8× §aAlle Spieler", Material.INK_SACK, 1, 10));
		}else if(playerHiderSettings.get(p) == Hider.Friends){
			p.getInventory().setItem(2, create("§8➟ §6Playerhider §8× §eFreunde", Material.INK_SACK, 1, 14));
			
		}else if(playerHiderSettings.get(p) == Hider.Nothing){
			p.getInventory().setItem(2, create("§8➟ §6Playerhider §8× §cKeine Spieler", Material.INK_SACK, 1, 1));
		}else{
			p.getInventory().setItem(2, create("§8➟ §4ERROR §8× §cKeine Spieler", Material.INK_SACK, 1, 1));
		}
	}
	public static void sendTitle(Player p){
		if(playerHiderSettings.get(p) == Hider.All){
			MessageAPI.sendTitle(p, "§aAlle Spieler", "§7werden dir angezeigt.",20);
		}else if(playerHiderSettings.get(p) == Hider.Friends){
			MessageAPI.sendTitle(p, "§eFreunde", "§7werden dir angezeigt.",20);
		}else{
			MessageAPI.sendTitle(p, "§cKeine Spieler", "§7werden dir angezeigt.",20);
		}
	}
	
	public static void setFunktion(Player p,Hider hider){
		
		
		if(hider == Hider.All){
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 999999));
			for(Player all:Bukkit.getOnlinePlayers()){
				p.showPlayer(all);
			}
		}else if(hider == Hider.Friends){

			if(!playerFriends.containsKey(p)){
				playerFriends.put(p, new Methods().getFriendList(p.getName()));
			}

			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 999999));
			for(Player all:Bukkit.getOnlinePlayers()){
				if(!playerFriends.get(p).contains(all.getName())){
					p.hidePlayer(all);
				}
			}
			
		}else if(hider == Hider.Nothing){
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 999999));
			for(Player all:Bukkit.getOnlinePlayers()){
				p.hidePlayer(all);
			}
		}
		
		
	}
	public static void changeSettings(Player p,Hider hider){
		if(hider == Hider.All){
			playerHiderSettings.remove(p);
			playerHiderSettings.put(p, Hider.Friends);
		}else if(hider == Hider.Friends){
			playerHiderSettings.remove(p);
			playerHiderSettings.put(p, Hider.Nothing);
		}else if(hider == Hider.Nothing){
			playerHiderSettings.remove(p);
			playerHiderSettings.put(p, Hider.All);
		}
	}
	
	public static void fromHashToMySql(Player p){
		try {
			setHiderSettings(p.getUniqueId().toString().replace("-", ""), playerHiderSettings.get(p));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	private static int getInt(String uuid) {
		PreparedStatement st;
		try {
			st = MySQL.con.prepareStatement("SELECT Wert FROM LBplayerhider WHERE UUID = ?");
			st.setString(1, uuid);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt("Wert");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static void setHiderSettings(String uuid, Hider hider) throws SQLException {

		if (!playerExists(uuid)) {
			createPlayer(uuid);
		}
		if(hider == Hider.All){
			PreparedStatement st = MySQL.con.prepareStatement("UPDATE LBplayerhider SET Wert = ? WHERE UUID = ?");
			st.setInt(1, 1);
			st.setString(2, uuid);
			st.executeUpdate();
		}else if(hider == Hider.Friends){
			PreparedStatement st = MySQL.con.prepareStatement("UPDATE LBplayerhider SET Wert = ? WHERE UUID = ?");
			st.setInt(1, 2);
			st.setString(2, uuid);
			st.executeUpdate();
		}else if(hider == Hider.Nothing){
			PreparedStatement st = MySQL.con.prepareStatement("UPDATE LBplayerhider SET Wert = ? WHERE UUID = ?");
			st.setInt(1, 3);
			st.setString(2, uuid);
			st.executeUpdate();
		}
	}
	
	public static boolean playerExists(String uuid) {
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM LBplayerhider WHERE UUID= '" + uuid + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void createPlayer(String uuid) {
		if (!playerExists(uuid)) {
			Main.mysql.update("INSERT INTO LBplayerhider (UUID, Wert) VALUES ('" + uuid+ "', '1');");
		}
	}
	private static ItemStack create(String name,Material material,int anzahl,int subid){
		ItemStack i = new ItemStack(material, anzahl, (short) subid);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

}
