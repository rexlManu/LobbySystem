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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.util.ItemBuilder;

public class Boots {

	public static HashMap<Player, BootsType> currentBoots = new HashMap<>();
	public static HashMap<Player, List<BootsType>> gekaufteBoots = new HashMap<>();
	
	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Wähle Boots aus.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		int i1 = 0+9+9;
		for(BootsType sp : BootsType.values()){
			i1++;
		inv.setItem(i1, getItemStack(sp, p));
		}
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		p.openInventory(inv);
	}
	public static ItemStack getItemStack(BootsType sp, Player p){
		if(!gekaufteBoots.containsKey(p)){
			update(p);
		}
		if(gekaufteBoots.get(p).contains(sp)){
			return new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getDisplayname()).setLore("§r","§aGekauft","§r").toItemStack();
		}else{
			return new ItemBuilder(sp.getGuiitem(), 1).setName(sp.getDisplayname()).setLore("§r","§7Preis","   §8➥ §a"+sp.getPrize(),"§r").toItemStack();
		}
	}
	
	public static void addToHashmap(Player p){
		gekaufteBoots.put(p, getBoots(p.getUniqueId().toString()));
	}
	
	public static void update(Player p){
		gekaufteBoots.remove(p);
		gekaufteBoots.put(p, getBoots(p.getUniqueId().toString()));
	}
	
	public static void setFunction(Player p){
		if(currentBoots.containsKey(p)){
			BootsType bt = currentBoots.get(p);
			p.removePotionEffect(PotionEffectType.SPEED);
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			p.removePotionEffect(PotionEffectType.JUMP);
			if(bt.equals(BootsType.Speed)){
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, true, false));
			}else if(bt.equals(BootsType.DoubleJump)){
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, true, false));
			}else if(bt.equals(BootsType.Fly)){
				if(p.isSneaking()){
					p.setVelocity(p.getLocation().getDirection().multiply(1.0).setY(0.5));
				}
			}else if(bt.equals(BootsType.Ghost)){
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 255, true, false));
			}
		}
	}
	public static void setBoots(Player p){
		if(currentBoots.containsKey(p)){
			BootsType bt = currentBoots.get(p);
			if(bt.equals(BootsType.Speed)){
				p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS, 1).setName(bt.getDisplayname()).toItemStack());
			}else if(bt.equals(BootsType.DoubleJump)){
				p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS, 1).setName(bt.getDisplayname()).toItemStack());
			}else if(bt.equals(BootsType.Fly)){
				p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS, 1).setName(bt.getDisplayname()).toItemStack());
			}else if(bt.equals(BootsType.Ghost)){
				p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS, 1).setName(bt.getDisplayname()).toItemStack());
			}
		}
	}
	
	public static void buyGUI(Player p, BootsType sp){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Kaufe§4§7: "+sp.getDisplayname());
		
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
	public static void buyConfirm(Player p, BootsType sp){
		int coins = 0;
		try {
			coins = CoinsAPI.getCoins(p.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(coins >= sp.getPrize()){
			
			CoinsAPI.removeCoins(p.getUniqueId().toString(), coins-sp.getPrize());
			p.closeInventory();
			addBoots(p.getUniqueId().toString(), sp);
			Main.sm.sound(Sound.LEVEL_UP, p);
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast §aerfolgreich §7die "+sp.getDisplayname()+"§7-Boots gekauft.");
			Boots.update(p);
			
		}else{
			p.closeInventory();
			Main.sm.sound(Sound.BAT_HURT, p);
			int fehlendecoins = sp.getPrize() - coins;
			p.sendMessage(Message.PREFIX.getValue()+"§7Dir §cfehlen §7noch §c"+fehlendecoins+"§7 Coins.");
		}
		
	}
	
	public static void putBootsOn(Player p , BootsType sp){
		if(currentBoots.containsKey(p)){
			currentBoots.remove(p);
		}
		currentBoots.put(p, sp);
	}
	
	public static BootsType fromGuiNameToType(String name){
		for(BootsType sp:BootsType.values()){
			if(sp.getDisplayname().contains(name)){
				return sp;
			}
		}
		return null;
	}
	
	public static void addBoots(String uuid,BootsType sp){
		if(!hasBoots(uuid, sp)){
			if(sp !=null){
				System.out.println("Test");
			}
			Main.mysql.update("INSERT INTO bootsExtras (UUID, Boots) VALUES ('" + uuid+ "', '"+sp.getMysqlname()+"');");
		}
	}
	public static boolean hasBoots(String uuid,BootsType sp){
		return getBoots(uuid).contains(sp);
	}
	public static List<BootsType> getBoots(String uuid){
		try {
			@SuppressWarnings("static-access")
			PreparedStatement State = Main.mysql.con.prepareStatement("SELECT * FROM bootsExtras WHERE UUID=?");
			State.setString(1, uuid);

			ResultSet Result = State.executeQuery();

			List<BootsType> list = new ArrayList<BootsType>();

			while (Result.next()) {
				list.add(fromMySQLNameToType(Result.getString("Boots")));
			}

			Result.close();
			State.close();

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static BootsType fromMySQLNameToType(String name){
		for(BootsType sp : BootsType.values()){
			if(sp.getMysqlname().equalsIgnoreCase(name)){
				return sp;
			}
		}
		return null;
	}
	
}
