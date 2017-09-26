package de.rexlmanu.lobby.methods;

import org.bukkit.Material;

public enum BootsType {

	
	Fly("§7Fly", "Fly", Material.FEATHER, 7500),
	Speed("§aSpeed", "Speed", Material.SUGAR, 2500),
	DoubleJump("§bDoublejump", "Doublejump", Material.SLIME_BALL, 5000),
	Ghost("§8Ghost", "Ghost", Material.BONE, 5000);
	
	private String displayname;
	private String mysqlname;
	private Material guiitem;
	private int prize;
	
	private BootsType(String displayname, String mysqlname, Material guiitem, int prize){
		this.displayname = displayname;
		this.mysqlname = mysqlname;
		this.guiitem = guiitem;
		this.prize = prize;
	}

	public String getDisplayname() {
		return displayname;
	}

	public String getMysqlname() {
		return mysqlname;
	}

	public Material getGuiitem() {
		return guiitem;
	}

	public int getPrize() {
		return prize;
	}
	
	
}
