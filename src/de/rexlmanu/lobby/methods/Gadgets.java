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
import org.bukkit.potion.PotionEffectType;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.util.ItemBuilder;

public class Gadgets {

	public static HashMap<Player, GadgetsType> currentGadgets = new HashMap<>();
	public static HashMap<Player, List<GadgetsType>> gekaufteGadgets = new HashMap<>();
	
	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Wähle Gadgets aus.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		int i1 = 0+9+9;
		for(GadgetsType sp : GadgetsType.values()){
			i1++;
		inv.setItem(i1, getItemStack(sp, p));
		}
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		p.openInventory(inv);
	}
	public static ItemStack getItemStack(GadgetsType sp, Player p){
		if(!gekaufteGadgets.containsKey(p)){
			update(p);
		}
		if(gekaufteGadgets.get(p).contains(sp)){
			return new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getDisplayname()).setLore("§r","§aGekauft","§r").toItemStack();
		}else{
			return new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getDisplayname()).setLore("§r","§7Preis","   §8➥ §a"+sp.getPrize(),"§r").toItemStack();
		}
	}
	
	public static void addToHashmap(Player p){
		gekaufteGadgets.put(p, getGadgets(p.getUniqueId().toString()));
	}
	
	public static void update(Player p){
		gekaufteGadgets.remove(p);
		gekaufteGadgets.put(p, getGadgets(p.getUniqueId().toString()));
	}
	
	public static void setFunction(Player p){
		if(currentGadgets.containsKey(p)){
			p.removePotionEffect(PotionEffectType.SPEED);
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			p.removePotionEffect(PotionEffectType.JUMP);
		}
	}
	public static void setGadgets(Player p){
		if(currentGadgets.containsKey(p)){
		}
	}
	
	public static void buyGUI(Player p, GadgetsType sp){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Kaufe§7: "+sp.getDisplayname());

		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		inv.setItem(2+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)10).setName("§8» §aKaufen §8× §7Preis: §c"+sp.getPrize()).toItemStack());
		inv.setItem(4+9+9, new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getDisplayname()).setLore("§r","§7Preis","   §8➥ §a"+sp.getPrize(),"§r").toItemStack());
		inv.setItem(6+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)1).setName("§8» §4Abrechen").toItemStack());
		
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		
		p.openInventory(inv);
	}
	public static void buyConfirm(Player p, GadgetsType sp){
		int coins = 0;
		try {
			coins = CoinsAPI.getCoins(p.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(coins >= sp.getPrize()){
			
			CoinsAPI.removeCoins(p.getUniqueId().toString(), coins-sp.getPrize());
			p.closeInventory();
			addGadgets(p.getUniqueId().toString(), sp);
			Main.sm.sound(Sound.LEVEL_UP, p);
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast §aerfolgreich §7die "+sp.getDisplayname()+"§7-Gadgets gekauft.");
			Gadgets.update(p);
			
		}else{
			p.closeInventory();
			Main.sm.sound(Sound.BAT_HURT, p);
			int fehlendecoins = sp.getPrize() - coins;
			p.sendMessage(Message.PREFIX.getValue()+"§7Dir §cfehlen §7noch §c"+fehlendecoins+"§7 Coins.");
		}
		
	}
	
	public static void putGadgetsOn(Player p , GadgetsType sp){
		if(currentGadgets.containsKey(p)){
			currentGadgets.remove(p);
		}
		currentGadgets.put(p, sp);
	}
	
	public static GadgetsType fromGuiNameToType(String name){
		for(GadgetsType sp:GadgetsType.values()){
			if(sp.getDisplayname().contains(name)){
				return sp;
			}
		}
		return null;
	}
	
	public static void addGadgets(String uuid,GadgetsType sp){
		if(!hasGadgets(uuid, sp)){
			if(sp !=null){
				System.out.println("Test");
			}
			Main.mysql.update("INSERT INTO gadgetsExtras (UUID, Gadgets) VALUES ('" + uuid+ "', '"+sp.getMysqlname()+"');");
		}
	}
	public static boolean hasGadgets(String uuid,GadgetsType sp){
		return getGadgets(uuid).contains(sp);
	}
	public static List<GadgetsType> getGadgets(String uuid){
		try {
			@SuppressWarnings("static-access")
			PreparedStatement State = Main.mysql.con.prepareStatement("SELECT * FROM gadgetsExtras WHERE UUID=?");
			State.setString(1, uuid);

			ResultSet Result = State.executeQuery();

			List<GadgetsType> list = new ArrayList<GadgetsType>();

			while (Result.next()) {
				list.add(fromMySQLNameToType(Result.getString("Gadgets")));
			}

			Result.close();
			State.close();

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static GadgetsType fromMySQLNameToType(String name){
		for(GadgetsType sp : GadgetsType.values()){
			if(sp.getMysqlname().equalsIgnoreCase(name)){
				return sp;
			}
		}
		return null;
	}
	
}
