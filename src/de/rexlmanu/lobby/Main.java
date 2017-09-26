package de.rexlmanu.lobby;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.rexlmanu.lobby.commands.Build;
import de.rexlmanu.lobby.commands.Lobby;
import de.rexlmanu.lobby.commands.Setsign;
import de.rexlmanu.lobby.commands.Spawn;
import de.rexlmanu.lobby.freundesystem.CookieAPI;
import de.rexlmanu.lobby.freundesystem.Methods;
import de.rexlmanu.lobby.freundesystem.listener.InventoryClickListener;
import de.rexlmanu.lobby.jumpandrun.JnRHandler;
import de.rexlmanu.lobby.listener.*;
import de.rexlmanu.lobby.methods.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import de.dytanic.cloudnet.api.CloudNetAPI;
//import de.dytanic.cloudnet.bukkitproxy.api.CloudServer;

public class Main extends JavaPlugin {

	public static File file;
	public static YamlConfiguration cfg;
	public static MySQL mysql;
	public static Main instance;
	public static SoundManage sm;
	private CookieAPI api;
	private Methods methods;
//	public static CloudNetAPI cloudapi;
//	public static CloudServer server;

	@Override
	public void onEnable() {
		instance = this;
		
		for(World world:Bukkit.getWorlds()){
			world.setDifficulty(Difficulty.PEACEFUL);
			world.setTime(0);
		}
		
//		server = CloudServer.getInstance();
//		cloudapi = CloudNetAPI.getInstance();
		Bukkit.getConsoleSender().sendMessage(Message.PREFIX.getValue() + "§aDas Plugin wurde aktiviert.");

		file = new File("LobbySystem/config.yml");
		cfg = YamlConfiguration.loadConfiguration(file);

		if (!file.exists()) {
			cfg.set("Lobby", "by rexlManu developed.");
			Bukkit.getConsoleSender().sendMessage(Message.PREFIX.getValue() + "§aConfigfile wurde erstellt.");
		}

		load();
		mysql = new MySQL("rexlGames.de", "database", "user", "password");
		MySQL.createTable();
		Rangs.load();
		Teleporter.loadLocs();
		sm = new SoundManage();
		
		fetchClasses();

		if(!getDataFolder().exists()){
			getDataFolder().mkdirs();
		}

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		SignLoader.load();
	}

	public void fetchClasses() {

		api = new CookieAPI(this, "");
		methods = new Methods(this);

	}
	
	public static MySQL getMysql() {
		return mysql;
	}
	
	public CookieAPI getAPI() {
		return api;
	}

	public Methods getFriendManager() {
		return methods;
	}

	public String getPrefix() {
		return Message.PREFIX.getValue();
	}
	
	private void load() {

		// Commands
		load(new Lobby(), "lobby");
		load(new Spawn(), "spawn");
		load(new Build(), "build");
		load(new Setsign(), "setsign");

		// Listener
		load(new Join());
		load(new Interact(this));
		load(new InventoryClick());
		load(new Place());
		load(new Break());
		load(new Damage());
		load(new Achievment());
		load(new Weather());
		load(new Food());
		load(new Quit());
		load(new Chat());
		load(new Pickup());
		load(new Drop());
		load(new EntitySpawn());
		load(new Move());
		load(new FarmDestroyer());
		load(new InventoryClickListener(this));
		
		Bukkit.getConsoleSender()
				.sendMessage(Message.PREFIX.getValue() + "§aAlle Commands und Listener wurden geladen.");
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Message.PREFIX.getValue() + "§cDas Plugin wurde deaktiviert.");

	}

	private void load(CommandExecutor cmd, String cmdname) {
		this.getCommand(cmdname).setExecutor(cmd);
	}

	private void load(Listener listener) {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(listener, this);
	}

	public static Main getInstance() {
		return instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("setjump")) {
			File Jdata = new File("plugins/LobbySystem", "Jumpandrun.yml");
			FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);
			List<String> JumpAndRuns = JumpFile.getStringList("Jumps");
			if (new RangManage(p).hasPermissions(Rangs.Admin)) {
				if (args.length == 1) {
					if (JumpAndRuns.contains(args[0])) {
						p.sendMessage(Message.PREFIX.getValue()+"§cDieses JumpAndRun existiert bereits.");
					} else {
						String jumpname = args[0];
						JnRHandler.createJumpAndRun(p, jumpname);
					}
				} else {
					p.sendMessage(Message.PREFIX.getValue()+"§6Korrekte Verwendung des Jump Systems§8:");
					p.sendMessage(Message.PREFIX.getValue()+"§8? §3/setjump <Name>");
					p.sendMessage(Message.PREFIX.getValue()+"§8§ §7Erstellt ein JumpAndRun, als spawn dient die Location des Spielers der den Command ausführt.");
					p.sendMessage(Message.PREFIX.getValue()+"§8? §3/removejump <Name>");
					p.sendMessage(Message.PREFIX.getValue()+"§8§ §7Entfernt ein JumpAndRun aus dem System.");
					p.sendMessage(Message.PREFIX.getValue()+"§8§ §cAm Ziel des JumpAndRuns muss ein Diamant Block gesetzt sein!");
				}
			} else {
				p.sendMessage(Message.PREFIX.getValue()+"§cDu hast nicht genügend Rechte.");
			}
		} else if (cmd.getName().equalsIgnoreCase("removejump")) {
			File Jdata = new File("plugins/LobbySystem", "Jumpandrun.yml");
			FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);

			List<String> JumpAndRuns = JumpFile.getStringList("Jumps");
			if (new RangManage(p).hasPermissions(Rangs.Admin)) {
				if (args.length == 1) {
					String jumpname = args[0];
					if (JumpAndRuns.contains(jumpname)) {
						JumpAndRuns.remove(jumpname);
						JumpFile.set("Jumps", JumpAndRuns);
						try {
							JumpFile.save(Jdata);
						} catch (IOException e) {
							e.printStackTrace();
						}
						JnRHandler.removeJumpAndRunLocation(p, jumpname);
						p.sendMessage("§aJumpAndRun §c" + jumpname + "§a erfolgreich entfernt!");
					} else {
						p.sendMessage("§cKein passendes JumpAndRun gefunden.");
					}
				} else {
					p.sendMessage(Message.PREFIX.getValue()+"§6Korrekte Verwendung des Jump Systems§8:");
					p.sendMessage(Message.PREFIX.getValue()+"§8? §3/setjump <Name>");
					p.sendMessage(Message.PREFIX.getValue()+"§8§ §7Erstellt ein JumpAndRun, als spawn dient die Location des Spielers der den Command ausführt.");
					p.sendMessage(Message.PREFIX.getValue()+"§8? §3/removejump <Name>");
					p.sendMessage(Message.PREFIX.getValue()+"§8§ §7Entfernt ein JumpAndRun aus dem System.");
					p.sendMessage(Message.PREFIX.getValue()+"§8§ §cAm Ziel des JumpAndRuns muss ein Diamant Block gesetzt sein!");
				}
			} else {
				p.sendMessage(Message.PREFIX.getValue()+"§cDu hast nicht gen§gend Rechte.");
			}

		return false;
	}
		return false;

}
	  public void sendToServer(Player player, String target)
	  {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Connect");
	    out.writeUTF(target);
	    player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	  }
}
