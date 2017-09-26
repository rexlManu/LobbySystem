package de.rexlmanu.lobby.methods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.rexlmanu.lobby.util.ItemBuilder;

public class Profil {



	public static void openGUI(Player p){
		Inventory inv = Bukkit.createInventory(null, 5*9, "§8Wähle eine Kategorien aus.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 5*9);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setOwner("MHF_Question");
		skullmeta.setDisplayName("§8» §5Köpfe");
		skull.setItemMeta(skullmeta);
		
		inv.setItem(2+9, new ItemBuilder(Material.GOLD_NUGGET, 1).setName("§8» §6Rangliste").toItemStack());
		inv.setItem(4+9, new ItemBuilder(Material.GOLDEN_APPLE, 1).setName("§8» §eGold-Rang").toItemStack());
		inv.setItem(6+9, new ItemBuilder(Material.NETHER_STAR, 1).setName("§8» §7Persönliche Stats").toItemStack());
		inv.setItem(1+9+9+9, new ItemBuilder(Material.REDSTONE, 1).setName("§8» §4Spuren").toItemStack());
		inv.setItem(3+9+9+9, new ItemBuilder(Material.LEATHER_BOOTS, 1).setName("§8» §3Boots").toItemStack());
		inv.setItem(5+9+9+9, new ItemBuilder(Material.EMERALD, 1).setName("§8» §aGadgets").toItemStack());
		inv.setItem(7+9+9+9, skull);
		
		p.openInventory(inv);
	}
}
