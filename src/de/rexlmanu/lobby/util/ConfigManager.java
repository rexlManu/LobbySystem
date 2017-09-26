package de.rexlmanu.lobby.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	public static void saveLocation(Location loc,File file,YamlConfiguration cfg,String path){
		cfg.set(path.toLowerCase()+".X", loc.getX());
		cfg.set(path.toLowerCase()+".Y", loc.getY());
		cfg.set(path.toLowerCase()+".Z", loc.getZ());
		cfg.set(path.toLowerCase()+".Yaw", loc.getYaw());
		cfg.set(path.toLowerCase()+".Pitch", loc.getPitch());
		cfg.set(path.toLowerCase()+".W", loc.getWorld().getName());
		try {
			cfg.save(file);
		} catch (IOException e) {
		}
	}
	public static Location getLocation(YamlConfiguration cfg,String path){
		if(!cfg.contains(path+".W")){
			return null;
		}
		return new Location(Bukkit.getWorld(cfg.getString(path.toLowerCase()+".W")), cfg.getDouble(path.toLowerCase()+".X"), cfg.getDouble(path.toLowerCase()+".Y"), cfg.getDouble(path.toLowerCase()+".Z"), (float) cfg.getDouble(path.toLowerCase()+".Yaw"), (float) cfg.getDouble(path.toLowerCase()+".Pitch"));
	}
	
}
