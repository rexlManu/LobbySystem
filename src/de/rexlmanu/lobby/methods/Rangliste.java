package de.rexlmanu.lobby.methods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.rexlmanu.lobby.util.ItemBuilder;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Rangliste {

	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Wähle einen Spielmodus aus.§r");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		inv.setItem(2+9+9, editLore(Teleporter.warp2, "§r","§7Schaue dir die Rangliste an."));
		inv.setItem(1+9+9, editLore(Teleporter.warp1, "§r","§7Schaue dir die Rangliste an."));
		inv.setItem(6+9+9, editLore(Teleporter.warp3, "§r","§7Schaue dir die Rangliste an."));
		inv.setItem(7+9+9, editLore(Teleporter.warp4, "§r","§7Schaue dir die Rangliste an."));
		inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());
		
		p.openInventory(inv);
		
	}
	private static ItemStack editLore(ItemStack i, String... lore){
		ItemStack itemStack = i;
		ItemMeta meta = i.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(meta);
		return itemStack;
	}
}
