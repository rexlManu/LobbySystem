package de.rexlmanu.lobby.methods;

import org.bukkit.Material;

public enum GadgetsType {

	Test("§4Test", "Test", Material.BARRIER, 999999);
	
	private String displayname;
	private String mysqlname;
	private Material guiitem;
	private int prize;
	
	private GadgetsType(String displayname, String mysqlname, Material guiitem, int prize){
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
