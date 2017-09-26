package de.rexlmanu.lobby.methods;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManage {

	@SuppressWarnings("deprecation")
	public static void sendTablist(Player p){
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Team admin = sb.registerNewTeam("A");
		admin.setPrefix(Rangs.Admin.getTablist());
		Team developer = sb.registerNewTeam("B");
		developer.setPrefix(Rangs.Developer.getTablist());
		Team moderator = sb.registerNewTeam("C");
		moderator.setPrefix(Rangs.Moderator.getTablist());
		Team supporter = sb.registerNewTeam("D");
		supporter.setPrefix(Rangs.Supporter.getTablist());
		Team youtuber = sb.registerNewTeam("E");
		youtuber.setPrefix(Rangs.Youtuber.getTablist());
		Team jryoutuber = sb.registerNewTeam("F");
		jryoutuber.setPrefix(Rangs.JrYoutuber.getTablist());
		Team platin = sb.registerNewTeam("G");
		platin.setPrefix(Rangs.Platin.getTablist());
		Team diamond = sb.registerNewTeam("H");
		diamond.setPrefix(Rangs.Diamond.getTablist());
		Team gold = sb.registerNewTeam("I");
		gold.setPrefix(Rangs.Gold.getTablist());
		Team spieler = sb.registerNewTeam("J");
		spieler.setPrefix(Rangs.Spieler.getTablist());
		
		for(Player all:Bukkit.getOnlinePlayers()){
			RangManage rm = new RangManage(all);
			
			if(rm.hasPermissions(Rangs.Admin)){
				admin.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Developer)){
				developer.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Moderator)){
				moderator.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Supporter)){
				supporter.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Youtuber)){
				youtuber.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.JrYoutuber)){
				jryoutuber.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Platin)){
				platin.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Diamond)){
				diamond.addPlayer(all);
			}else if(rm.hasPermissions(Rangs.Gold)){
				gold.addPlayer(all);
			}else{
				spieler.addPlayer(all);
			}
		}
		p.setScoreboard(sb);
	}
}
