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
import org.bukkit.inventory.meta.SkullMeta;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.util.ItemBuilder;

public class Heads {

	public static HashMap<Player, HeadsType> currentHeads = new HashMap<>();
	public static HashMap<Player, List<HeadsType>> gekaufteHeads = new HashMap<>();
	
	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Wähle Köpfe aus.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		int i1 = 0+9+9;
		for(HeadsType sp : HeadsType.values()){
			i1++;
		inv.setItem(i1, getItemStack(sp, p));
		}
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		p.openInventory(inv);
	}
	public static ItemStack getItemStack(HeadsType sp, Player p){
		if(!gekaufteHeads.containsKey(p)){
			update(p);
		}
		if(gekaufteHeads.get(p).contains(sp)){
			
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
			skullmeta.setDisplayName(sp.getDisplayname());
			List<String> lore = new ArrayList<>();
			lore.add("§r");
			lore.add("§aGekauft");
			lore.add("§r");
			skullmeta.setLore(lore);
			skullmeta.setOwner(sp.getIngamename());
			skull.setItemMeta(skullmeta);
			
			return skull;
		}else{
			
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
			skullmeta.setDisplayName(sp.getDisplayname());
			List<String> lore = new ArrayList<>();
			lore.add("§r");
			lore.add("§7Preis");
			lore.add("   §8➥ §a"+sp.getPrize());
			lore.add("§r");
			skullmeta.setLore(lore);
			skullmeta.setOwner(sp.getIngamename());
			skull.setItemMeta(skullmeta);
			
			return skull;
		}
	}
	
	public static void setFunktion(Player p, HeadsType sp){
		p.getInventory().setHelmet(null);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setDisplayName(sp.getDisplayname());
		skullmeta.setOwner(sp.getIngamename());
		skull.setItemMeta(skullmeta);
		
		p.getInventory().setHelmet(skull);
	}
	
	public static void addToHashmap(Player p){
		gekaufteHeads.put(p, getHeads(p.getUniqueId().toString()));
	}
	
	public static void update(Player p){
		gekaufteHeads.remove(p);
		gekaufteHeads.put(p, getHeads(p.getUniqueId().toString()));
	}
	
	public static void setHeads(Player p){
		if(currentHeads.containsKey(p)){
		}
	}
	
	public static void buyGUI(Player p, HeadsType sp){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Kaufe§r§7: "+sp.getDisplayname());
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		inv.setItem(2+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)10).setName("§8» §aKaufen §8× §7Preis: §c"+sp.getPrize()).toItemStack());
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setDisplayName(sp.getDisplayname());
		List<String> lore = new ArrayList<>();
		lore.add("§r");
		lore.add("§7Preis");
		lore.add("   §8➥ §a"+sp.getPrize());
		lore.add("§r");
		skullmeta.setLore(lore);
		skullmeta.setOwner(sp.getIngamename());
		skull.setItemMeta(skullmeta);
		
		inv.setItem(6+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)1).setName("§8» §4Abrechen").toItemStack());
		inv.setItem(4+9+9, skull);
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		
		p.openInventory(inv);
	}
	public static void buyConfirm(Player p, HeadsType sp){
		int coins = 0;
		try {
			coins = CoinsAPI.getCoins(p.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(coins >= sp.getPrize()){
			
			CoinsAPI.removeCoins(p.getUniqueId().toString(), coins-sp.getPrize());
			p.closeInventory();
			addHeads(p.getUniqueId().toString(), sp);
			Main.sm.sound(Sound.LEVEL_UP, p);
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast §aerfolgreich §7die "+sp.getDisplayname()+"§7-Heads gekauft.");
			Heads.update(p);
			
		}else{
			p.closeInventory();
			Main.sm.sound(Sound.BAT_HURT, p);
			int fehlendecoins = sp.getPrize() - coins;
			p.sendMessage(Message.PREFIX.getValue()+"§7Dir §cfehlen §7noch §c"+fehlendecoins+"§7 Coins.");
		}
		
	}
	
	public static void putHeadsOn(Player p , HeadsType sp){
		if(currentHeads.containsKey(p)){
			currentHeads.remove(p);
		}
		currentHeads.put(p, sp);
	}
	
	public static HeadsType fromGuiNameToType(String name){
		for(HeadsType sp:HeadsType.values()){
			if(sp.getDisplayname().equalsIgnoreCase(name)){
				return sp;
			}
		}
		Bukkit.getConsoleSender().sendMessage("TEST ERROR");
		return null;
	}
	
	public static void addHeads(String uuid,HeadsType sp){
		if(!hasHeads(uuid, sp)){
			if(sp !=null){
				System.out.println("Test");
			}
			Main.mysql.update("INSERT INTO headsExtras (UUID, Heads) VALUES ('" + uuid+ "', '"+sp.getMysqlname()+"');");
		}
	}
	public static boolean hasHeads(String uuid,HeadsType sp){
		return getHeads(uuid).contains(sp);
	}
	public static List<HeadsType> getHeads(String uuid){
		try {
			@SuppressWarnings("static-access")
			PreparedStatement State = Main.mysql.con.prepareStatement("SELECT * FROM headsExtras WHERE UUID=?");
			State.setString(1, uuid);

			ResultSet Result = State.executeQuery();

			List<HeadsType> list = new ArrayList<HeadsType>();

			while (Result.next()) {
				list.add(fromMySQLNameToType(Result.getString("Heads")));
			}

			Result.close();
			State.close();

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static HeadsType fromMySQLNameToType(String name){
		for(HeadsType sp : HeadsType.values()){
			if(sp.getMysqlname().equalsIgnoreCase(name)){
				return sp;
			}
		}
		return null;
	}
	
}
