package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.rexlmanu.lobby.methods.BuildManager;

public class Place implements Listener{

	@EventHandler
	public void place(BlockPlaceEvent e){
		if(BuildManager.canBuild(e.getPlayer())){
		e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}
}
