package de.rexlmanu.lobby.jumpandrun;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.rexlmanu.lobby.methods.Message;

public class JnRHandler {

	public static void saveJumpFile()
	  {
	    File Jdata = new File("plugins/LobbySystem", "Jumpandrun.yml");
	    FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);
	    try
	    {
	      JumpFile.save(Jdata);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  public static boolean JumpAndRunExists(String name)
	  {
	    File Jdata = new File("plugins/LobbySystem", "Jumpandrun.yml");
	    FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);
	    List<String> JumpAndRuns = JumpFile.getStringList("Jumps");
	    if (JumpAndRuns.contains(name)) {
	      return true;
	    }
	    return false;
	  }
	  
	  public static void createJumpAndRun(Player p, String name)
	  {
	    File Jdata = new File("plugins/LobbySystem", "Jumpandrun.yml");
	    FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);
	    List<String> JumpAndRuns = JumpFile.getStringList("Jumps");
	    if (!JumpAndRunExists(name))
	    {
	      JumpFile.set(name + ".world", p.getLocation().getWorld().getName());
	      JumpFile.set(name + ".x", Double.valueOf(p.getLocation().getX()));
	      JumpFile.set(name + ".y", Double.valueOf(p.getLocation().getY()));
	      JumpFile.set(name + ".z", Double.valueOf(p.getLocation().getZ()));
	      JumpFile.set(name + ".yaw", Float.valueOf(p.getLocation().getYaw()));
	      JumpFile.set(name + ".pitch", Float.valueOf(p.getLocation().getPitch()));
	      JumpAndRuns.add(name);
	      JumpFile.set("Jumps", JumpAndRuns);
	      try
	      {
	        JumpFile.save(Jdata);
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      p.sendMessage(Message.PREFIX.getValue()+"§aDu hast das JumpAndRun §6" + name + " §aerstellt!");
	    }
	    else
	    {
	      p.sendMessage(Message.PREFIX.getValue()+"§cDieses JumpAndRun existiert bereits!");
	      p.sendMessage(Message.PREFIX.getValue()+"§cEntfernen mit /removejump " + name);
	    }
	  }
	  
	  public static void removeJumpAndRunLocation(Player p, String name)
	  {
	    File Jdata = new File("plugins/LobbySystem", "Jumpandrun.yml");
	    FileConfiguration JumpFile = YamlConfiguration.loadConfiguration(Jdata);
	    
	    JumpFile.set(name + ".world", null);
	    JumpFile.set(name + ".x", null);
	    JumpFile.set(name + ".y", null);
	    JumpFile.set(name + ".z", null);
	    JumpFile.set(name + ".yaw", null);
	    JumpFile.set(name + ".pitch", null);
	    try
	    {
	      JumpFile.save(Jdata);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
	
}
