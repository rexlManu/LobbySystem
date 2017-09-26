package de.rexlmanu.lobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rexlmanu.lobby.methods.BuildManager;
import de.rexlmanu.lobby.methods.Message;

public class Build implements CommandExecutor{
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender s, Command c, String aaaa, String[] ss) {
		if(!(s instanceof Player)){
			s.sendMessage(Message.CONSOLESENDER.getValue());
			return true;
		}
		Player p = (Player)s;
		BuildManager bm = new BuildManager();
		if(bm.canBuild(p)){
			bm.disableBuild(p);
		}else{
			bm.enableBuild(p);
		}
		return false;
	}

}
