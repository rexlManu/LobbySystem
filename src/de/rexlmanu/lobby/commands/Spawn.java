package de.rexlmanu.lobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rexlmanu.lobby.methods.Message;
import de.rexlmanu.lobby.methods.Teleporter;
import de.rexlmanu.lobby.methods.Teleporter.TeleporterLocs;

public class Spawn implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String aaaa, String[] ss) {
		if(!(s instanceof Player)){
			s.sendMessage(Message.CONSOLESENDER.getValue());
			return true;
		}
		Player p = (Player)s;
		
		if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.spawn)){
			p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
			return true;
		}
		p.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.spawn));
		
		return false;
	}

}
