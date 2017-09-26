package de.rexlmanu.lobby.jumpandrun;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class JnRLocations {

	static File Jdata = new File("plugins/Lobby", "Jumpandrun.yml");
	  static FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);
	  
	  public static Location getJumpAndRun(String name)
	  {
	    Location loc = null;
	    String world = JumpFile.getString(name + ".world");
	    double x = JumpFile.getDouble(name + ".x");
	    double y = JumpFile.getDouble(name + ".y");
	    double z = JumpFile.getDouble(name + ".z");
	    float yaw = (float)JumpFile.getDouble(name + ".yaw");
	    float pitch = (float)JumpFile.getDouble(name + ".pitch");
	    loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	    return loc;
	  }
	
}
