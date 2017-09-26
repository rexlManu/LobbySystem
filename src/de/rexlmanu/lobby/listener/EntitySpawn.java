package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawn implements Listener{
	
	@EventHandler
	public void entitypspawn(EntitySpawnEvent e){
		if(e.getEntity().isCustomNameVisible() == false){
			e.getEntity().remove();
		}
	}

}
