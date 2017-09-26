package de.rexlmanu.lobby.methods;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.rexlmanu.lobby.util.ItemBuilder;

public class Stats {

	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Deine persönlichen Stats.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		
		ItemStack warp1 = Teleporter.warp1;
		warp1.getItemMeta().setLore(getLore(p, "Test"));
		warp1.setItemMeta(warp1.getItemMeta());
		ItemStack warp2 = Teleporter.warp2;
		warp2.getItemMeta().setLore(getLore(p, "Test"));
		warp2.setItemMeta(warp1.getItemMeta());
		ItemStack warp3 = Teleporter.warp3;
		warp3.getItemMeta().setLore(getLore(p, "Test"));
		warp3.setItemMeta(warp1.getItemMeta());
		ItemStack warp4 = Teleporter.warp4;
		warp4.getItemMeta().setLore(getLore(p, "Test"));
		warp4.setItemMeta(warp1.getItemMeta());
		ItemStack warp5 = Teleporter.warp5;
		warp5.getItemMeta().setLore(getLore(p, "Test"));
		warp5.setItemMeta(warp1.getItemMeta());
		ItemStack warp6 = Teleporter.warp6;
		warp6.getItemMeta().setLore(getLore(p, "Test"));
		warp6.setItemMeta(warp1.getItemMeta());
		
		inv.setItem(1+9+9, warp1);
		inv.setItem(3+9+9, warp2);
		inv.setItem(5+9+9, warp3);
		inv.setItem(7+9+9, warp4);
		inv.setItem(4+9+9+9, warp5);
		inv.setItem(4+9, warp6);
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		
		p.openInventory(inv);
		
	}
	
	public static List<String> getLore(Player p,String spielmodus){
		List<String> lore = new ArrayList<>();
		
		lore.add("§8§m---------------");
		lore.add("§8● §aKills§7: §a100");
		lore.add("§8● §cDeaths§7: §c100");
		lore.add("§8● §2Wins§7: §2100");
		lore.add("§8● §fPlayedgames§7: §f100");
		lore.add("§8§m---------------");
		
		return lore;
	}
	
	
}
