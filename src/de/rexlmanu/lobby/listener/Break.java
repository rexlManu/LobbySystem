package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.rexlmanu.lobby.methods.BuildManager;

public class Break implements Listener{
	
	@EventHandler
	public void breakk(BlockBreakEvent e){
		if(BuildManager.canBuild(e.getPlayer())){
		e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}

}
