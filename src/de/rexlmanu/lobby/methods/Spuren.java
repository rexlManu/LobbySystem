package de.rexlmanu.lobby.methods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.util.ItemBuilder;

public class Spuren {

	public static HashMap<Player, SpurenType> currentSpuren = new HashMap<>();
	public static HashMap<Player, List<SpurenType>> gekaufteSpuren = new HashMap<>();
	
	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Wähle eine Spur aus.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		int i1 = 0+9+9;
		for(SpurenType sp : SpurenType.values()){
			i1++;
		inv.setItem(i1, getItemStack(sp, p));
		}
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		p.openInventory(inv);
	}
	public static ItemStack getItemStack(SpurenType sp, Player p){
		if(!gekaufteSpuren.containsKey(p)){
			update(p);
		}
		if(gekaufteSpuren.get(p).contains(sp)){
			return new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getName()).setLore("§r","§aGekauft","§r").toItemStack();
		}else{
			return new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getName()).setLore("§r","§7Preis","   §8➥ §a"+sp.getPrize(),"§r").toItemStack();
		}
	}
	
	public static void addToHashmap(Player p){
		gekaufteSpuren.put(p, getSpuren(p.getUniqueId().toString()));
	}
	
	public static void update(Player p){
		gekaufteSpuren.remove(p);
		gekaufteSpuren.put(p, getSpuren(p.getUniqueId().toString()));
	}
	
	public static void buyGUI(Player p, SpurenType sp){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Kaufe§1§7: "+sp.getName());
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		inv.setItem(2+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)10).setName("§8» §aKaufen §8× §7Preis: §c"+sp.getPrize()).toItemStack());
		inv.setItem(4+9+9, new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getName()).setLore("§r","§7Preis","   §8➥ §a"+sp.getPrize(),"§r").toItemStack());
		inv.setItem(6+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)1).setName("§8» §4Abrechen").toItemStack());
		
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		
		p.openInventory(inv);
	}
	public static void buyConfirm(Player p, SpurenType sp){
		int coins = 0;
		try {
			coins = CoinsAPI.getCoins(p.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(coins >= sp.getPrize()){
			
			CoinsAPI.removeCoins(p.getUniqueId().toString(), coins-sp.getPrize());
			p.closeInventory();
			addSpuren(p.getUniqueId().toString(), sp);
			Main.sm.sound(Sound.LEVEL_UP, p);
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast §aerfolgreich §7die "+sp.getName()+"§7-Spuren gekauft.");
			Spuren.update(p);
			
		}else{
			p.closeInventory();
			Main.sm.sound(Sound.BAT_HURT, p);
			int fehlendecoins = sp.getPrize() - coins;
			p.sendMessage(Message.PREFIX.getValue()+"§7Dir §cfehlen §7noch §c"+fehlendecoins+"§7 Coins.");
		}
		
	}
	
	public static void putSpurenOn(Player p , SpurenType sp){
		if(currentSpuren.containsKey(p)){
			currentSpuren.remove(p);
		}
		currentSpuren.put(p, sp);
	}
	
	public static SpurenType fromGuiNameToType(String name){
		for(SpurenType sp:SpurenType.values()){
			if(sp.getName().contains(name)){
				return sp;
			}
		}
		return null;
	}
	
	public static void addSpuren(String uuid,SpurenType sp){
		if(!hasSpuren(uuid, sp)){
			if(sp !=null){
				System.out.println("Test");
			}
			Main.mysql.update("INSERT INTO spurenExtras (UUID, Spuren) VALUES ('" + uuid+ "', '"+sp.getMysqlname()+"');");
		}
	}
	public static boolean hasSpuren(String uuid,SpurenType sp){
		return getSpuren(uuid).contains(sp);
	}
	public static List<SpurenType> getSpuren(String uuid){
		try {
			@SuppressWarnings("static-access")
			PreparedStatement State = Main.mysql.con.prepareStatement("SELECT * FROM spurenExtras WHERE UUID=?");
			State.setString(1, uuid);

			ResultSet Result = State.executeQuery();

			List<SpurenType> list = new ArrayList<SpurenType>();

			while (Result.next()) {
				list.add(fromMySQLNameToType(Result.getString("Spuren")));
			}

			Result.close();
			State.close();

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static SpurenType fromMySQLNameToType(String name){
		for(SpurenType sp : SpurenType.values()){
			if(sp.getMysqlname().equalsIgnoreCase(name)){
				return sp;
			}
		}
		return null;
	}
}
