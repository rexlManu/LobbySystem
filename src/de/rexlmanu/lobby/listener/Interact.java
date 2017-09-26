package de.rexlmanu.lobby.listener;

import de.rexlmanu.lobby.methods.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.rexlmanu.lobby.Main;

public class Interact implements Listener{
	
	Main main;
	
	public Interact(Main main){
		this.main = main;
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && !BuildManager.canBuild(p)){
			e.setCancelled(true);
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				if(e.getClickedBlock() !=null){
					if(e.getClickedBlock().getType() == Material.SIGN
							|| e.getClickedBlock().getType() == Material.WALL_SIGN){
						Sign s = (Sign) e.getClickedBlock().getState();
						String st = s.getLine(1).replace("- ", "").replace("§0- ", "").replace(" §0-", "");
						Main.getInstance().sendToServer(p, st);
						System.out.println(st+"- TEST");
					}
				}
			}
			if(e.getItem() !=null){
				if(e.getItem().hasItemMeta()){
					String displayname = e.getItem().getItemMeta().getDisplayName();
					if(displayname.equals("§8➟ §cGadgetsslots §8× §7§oRechtsklick")){
						p.sendMessage(Message.PREFIX.getValue()+"§cBitte wähle erst ein Gadget aus.");
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
					}else if(displayname.equals("§8➟ §6Teleporter §8× §7§oRechtsklick")){
						Teleporter.open(p);
						Main.sm.sound(Sound.CLICK, p);
					}else if(displayname.equals("§8➟ §aProfil §8× §7§oRechtsklick")){
						Profil.openGUI(p);
						Main.sm.sound(Sound.CLICK, p);
					}else if(e.getItem().getItemMeta().getDisplayName().contains("Playerhider")){
						Playerhider.changeSettings(p, Playerhider.playerHiderSettings.get(p));
						Playerhider.giveItem(p);
						Playerhider.sendTitle(p);
						Playerhider.setFunktion(p, Playerhider.playerHiderSettings.get(p));
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
					}else if(e.getItem().getItemMeta().getDisplayName().contains("§8➟ §eFreunde §8× §7§oRechtsklick")){
						main.getFriendManager().loadFriendInventory(p, 1);
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
					}else if(e.getItem().getItemMeta().getDisplayName().contains("§8➟ §9Lobbywechsler §8× §7§oRechtsklick")){
						Lobbywechsler.open(p);
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
					}
				}
			}
		}
	}
	@EventHandler
	public void on(PlayerArmorStandManipulateEvent e){
		if(!BuildManager.canBuild(e.getPlayer())){
			e.setCancelled(true);
		}
	}
}
