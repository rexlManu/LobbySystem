package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.rexlmanu.lobby.methods.BuildManager;

public class Drop implements Listener{
	
	@EventHandler
	public void drop(PlayerDropItemEvent e){
		if(BuildManager.canBuild(e.getPlayer())){
			return;
		}else{
			e.setCancelled(true);
		}
	}

}
