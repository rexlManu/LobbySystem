package de.rexlmanu.lobby.methods;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.util.ItemBuilder;

public class Premiumkauf {

	public static int goldrangkosten = 50000;
	
	public static void open(Player p){
		
		if(!(new RangManage(p).getRang() == Rangs.Spieler)){
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast schon einen Rang der mind. §6Gold-Rechte §7hat.");
			Main.sm.sound(Sound.BAT_HURT, p);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Goldkauf-Vorgang");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		inv.setItem(2+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)10).setName("§8» §aKaufen §8× §7Preis: §c"+goldrangkosten).toItemStack());
		inv.setItem(6+9+9, new ItemBuilder(Material.INK_SACK, 1,(byte)1).setName("§8» §4Abrechen").toItemStack());
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		
		p.openInventory(inv);
		
	}
	public static void confirmBuy(Player p){
		
		int coins = 0;
		try {
			coins = CoinsAPI.getCoins(p.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(coins >= goldrangkosten){
			
			CoinsAPI.removeCoins(p.getUniqueId().toString(), coins-goldrangkosten);
			p.closeInventory();
			Main.sm.sound(Sound.LEVEL_UP, p);
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast dir §aerfolgreich §7den §6Gold-Rang §7gekauft.");
			new RangManage(p).setRang(Rangs.Gold);
			
		}else{
			p.closeInventory();
			Main.sm.sound(Sound.BAT_HURT, p);
			int fehlendecoins = goldrangkosten - coins;
			p.sendMessage(Message.PREFIX.getValue()+"§7Dir §cfehlen §7noch §c"+fehlendecoins+"§7 Coins.");
		}
		
	}
	
}
