package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Food implements Listener{
	
	@EventHandler
	public void food(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}

}
