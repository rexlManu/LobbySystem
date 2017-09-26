package de.rexlmanu.lobby.freundesystem;

import org.bukkit.plugin.java.JavaPlugin;

public class FriendsGUI extends JavaPlugin{

	private MySQL mysql;
	private CookieAPI api;

	private Methods methods;

	public void onEnable() {

		fetchClasses();

		if(!getDataFolder().exists()){
			getDataFolder().mkdirs();
		}
		getMySQL().createMySQLFile("FriendsGUI");

		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	public void onDisable() {
		getMySQL().close();
	}

	public void fetchClasses() {

		mysql = new MySQL(this);
		

	}

	public MySQL getMySQL() {
		return mysql;
	}

	public CookieAPI getAPI() {
		return api;
	}

	public Methods getFriendManager() {
		return methods;
	}

	public String getPrefix() {
		return "§eFreunde §8▌ §r";
	}

}
