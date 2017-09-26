package de.rexlmanu.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rexlmanu.lobby.methods.Playerhider;

public class Quit implements Listener{
	
	@EventHandler
	public void quit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		Playerhider.fromHashToMySql(p);
	}

}
