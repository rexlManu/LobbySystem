package de.rexlmanu.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.rexlmanu.lobby.methods.RangManage;

public class Chat implements Listener{
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		RangManage rm = new RangManage(p);
		e.setFormat(rm.getRang().getPrefix()+" §8● §7"+p.getName()+"§8» §7"+e.getMessage());
	}

}
