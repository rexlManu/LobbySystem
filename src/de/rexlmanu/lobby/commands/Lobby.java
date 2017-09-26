package de.rexlmanu.lobby.commands;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.methods.Message;
import de.rexlmanu.lobby.methods.OfflineSupport;
import de.rexlmanu.lobby.methods.RangManage;
import de.rexlmanu.lobby.methods.Rangs;
import de.rexlmanu.lobby.util.ConfigManager;

public class Lobby implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String aaaa, String[] ss) {
		if(!(s instanceof Player)){
			s.sendMessage(Message.CONSOLESENDER.getValue());
			return true;
		}
		Player p = (Player)s;
		if(!new RangManage(p).hasPermissions(Rangs.Admin)){
			p.sendMessage(Message.NOPERMISSION.getValue());
			return true;
		}

		if(ss.length == 2){
			if(ss[0].equalsIgnoreCase("setloc")){
				String name = ss[1];
				name = name.toLowerCase();
				ConfigManager.saveLocation(p.getLocation(), Main.file, Main.cfg, name);
				p.sendMessage(Message.PREFIX.getValue()+"§7Die Location §a"+name+"§7 wurde erfolgreich gesetzt.");
			}else{
				sendHelp(p);
			}
		}else if(ss.length == 3){
			sendHelp(p);
		}else{
			sendHelp(p);
		}
		
		return false;
	}

	private void sendHelp(Player p) {
		p.sendMessage("§8»m---------------");
		p.sendMessage("§6Lobby-Help");
		p.sendMessage("§8* §7/lobby setloc <Name>");
		p.sendMessage("§8* §7/lobby setrang <Name> <Rang>");
		p.sendMessage("§8»m---------------");
	}
}
