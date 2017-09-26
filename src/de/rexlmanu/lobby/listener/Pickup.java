package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.rexlmanu.lobby.methods.BuildManager;

public class Pickup implements Listener{
	
	@EventHandler
	public void pickup(PlayerPickupItemEvent e){
			if(BuildManager.canBuild(e.getPlayer())){
				return;
			}else{
				e.setCancelled(true);
			}
	}

}
