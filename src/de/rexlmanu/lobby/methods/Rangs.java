package de.rexlmanu.lobby.methods;

import java.util.ArrayList;
import java.util.List;

public enum Rangs {

	Admin("§4Admin", "§4", "§4§lA§8 ▏ §7"),
	Developer("§3Developer", "§3", "§3§lD§8 ▏ §7"),
	Moderator("§cModerator", "§c", "§c§lM§8 ▏ §7"),
	Supporter("§eSupporter", "§e", "§e§lS§8 ▏ §7"),
	Youtuber("§5Youtuber", "§5", "§5§lY§8 ▏ §7"),
	JrYoutuber("§dJrYoutuber", "§d", "§d§lJ§8 ▏ §7"),
	Platin("§9Platin", "§9", "§9§lP§8 ▏ §7"),
	Diamond("§bDiamond", "§b", "§b§lD§8 ▏ §7"),
	Gold("§6Gold", "§6", "§6§lG§8 ▏ §7"),
	Spieler("§aSpieler", "§a", "§a§lS§8 ▏ §7");
	
	private String prefix;
	private String color;
	private String tablist;
	
	private Rangs(String prefix,String color,String tablist){
		this.prefix = prefix;
		this.color = color;
		this.tablist = tablist;
	}
	
	
	public String getPrefix() {
		return prefix;
	}

	public String getColor() {
		return color;
	}

	public String getTablist() {
		return tablist;
	}


	public static List<Rangs> admin;
	public static List<Rangs> developer;
	public static List<Rangs> moderator;
	public static List<Rangs> supporter;
	public static List<Rangs> youtuber;
	public static List<Rangs> jryoutuber;
	public static List<Rangs> platin;
	public static List<Rangs> diamond;
	public static List<Rangs> gold;
	public static List<Rangs> spieler;
	
	public static void load(){
		
		admin = new ArrayList<>();
		admin.add(Admin);
		admin.add(Developer);
		admin.add(Moderator);
		admin.add(Supporter);
		admin.add(Youtuber);
		admin.add(JrYoutuber);
		admin.add(Platin);
		admin.add(Diamond);
		admin.add(Gold);
		admin.add(Spieler);
		
		developer = new ArrayList<>();
		developer.add(Developer);
		developer.add(Moderator);
		developer.add(Supporter);
		developer.add(Youtuber);
		developer.add(JrYoutuber);
		developer.add(Platin);
		developer.add(Diamond);
		developer.add(Gold);
		developer.add(Spieler);
		
		moderator = new ArrayList<>();
		moderator.add(Moderator);
		moderator.add(Supporter);
		moderator.add(Youtuber);
		moderator.add(JrYoutuber);
		moderator.add(Platin);
		moderator.add(Diamond);
		moderator.add(Gold);
		moderator.add(Spieler);
		
		supporter = new ArrayList<>();
		supporter.add(Supporter);
		supporter.add(Youtuber);
		supporter.add(JrYoutuber);
		supporter.add(Platin);
		supporter.add(Diamond);
		supporter.add(Gold);
		supporter.add(Spieler);
		
		youtuber = new ArrayList<>();
		youtuber.add(Youtuber);
		youtuber.add(JrYoutuber);
		youtuber.add(Platin);
		youtuber.add(Diamond);
		youtuber.add(Gold);
		youtuber.add(Spieler);
		
		jryoutuber = new ArrayList<>();
		jryoutuber.add(JrYoutuber);
		jryoutuber.add(Platin);
		jryoutuber.add(Diamond);
		jryoutuber.add(Gold);
		jryoutuber.add(Spieler);
		
		platin = new ArrayList<>();
		platin.add(Platin);
		platin.add(Diamond);
		platin.add(Gold);
		platin.add(Spieler);
		
		diamond = new ArrayList<>();
		diamond.add(Diamond);
		diamond.add(Gold);
		diamond.add(Spieler);
		
		gold = new ArrayList<>();
		gold.add(Gold);
		gold.add(Spieler);
		
		spieler = new ArrayList<>();
		spieler.add(Spieler);
		
	}
	
	public List<Rangs> getList(Rangs rang){
		if(rang.equals(Admin)){
			return admin;
		}else if(rang.equals(Developer)){
			return developer;
		}else if(rang.equals(Moderator)){
			return moderator;
		}else if(rang.equals(Supporter)){
			return supporter;
		}else if(rang.equals(Youtuber)){
			return youtuber;
		}else if(rang.equals(JrYoutuber)){
			return jryoutuber;
		}else if(rang.equals(Platin)){
			return platin;
		}else if(rang.equals(Diamond)){
			return diamond;
		}else if(rang.equals(Gold)){
			return gold;
		}else if(rang.equals(Spieler)){
			return spieler;
		}
		return spieler;
	}
	
	
}
