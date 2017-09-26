package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class Achievment implements Listener{
	
	@EventHandler
	public void achievment(PlayerAchievementAwardedEvent e){
		e.setCancelled(true);
	}

}
