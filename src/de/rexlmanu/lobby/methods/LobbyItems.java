package de.rexlmanu.lobby.methods;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import de.rexlmanu.lobby.util.ItemBuilder;

public class LobbyItems {

	public static void giveItems(Player p){
		
		p.getInventory().setItem(4, new ItemBuilder(Material.COMPASS, 1).setName("§8➟ §6Teleporter §8× §7§oRechtsklick").toItemStack());
		p.getInventory().setItem(6, new ItemBuilder(Material.CHEST, 1).setName("§8➟ §aProfil §8× §7§oRechtsklick").toItemStack());
		p.getInventory().setItem(8, new ItemBuilder(Material.SKULL_ITEM, 1,(byte) 3).setName("§8➟ §eFreunde §8× §7§oRechtsklick").setSkullOwner(p.getName()).toItemStack());
		p.getInventory().setItem(0, new ItemBuilder(Material.NETHER_STAR, 1).setName("§8➟ §9Lobbywechsler §8× §7§oRechtsklick").toItemStack());
		
	}
	
}
