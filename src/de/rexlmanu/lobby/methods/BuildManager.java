package de.rexlmanu.lobby.methods;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.rexlmanu.lobby.Main;

public class BuildManager {

	private static ArrayList<Player> canBuild = new ArrayList<>();
	
	public static void enableBuild(Player p){
		if(new RangManage(p).hasPermissions(Rangs.Admin)){
			
			p.sendMessage(Message.PREFIX.getValue()+"§7Du hast den Baumodus §aaktiviert§7.");
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setGameMode(GameMode.CREATIVE);
			canBuild.add(p);
			Main.sm.sound(Sound.LEVEL_UP, p);
		}else{
			p.sendMessage(Message.NOPERMISSION.getValue());
		}
	}
	public static void disableBuild(Player p){
		p.sendMessage(Message.PREFIX.getValue()+"§7Du hast den Baumodus §cdeaktiviert§7.");
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(GameMode.ADVENTURE);
		LobbyItems.giveItems(p);
		canBuild.remove(p);
		Playerhider.giveItem(p);
		Main.sm.sound(Sound.NOTE_SNARE_DRUM, p);
	}
	
	public static boolean canBuild(Player p){
		return canBuild.contains(p);
	}
	
}
