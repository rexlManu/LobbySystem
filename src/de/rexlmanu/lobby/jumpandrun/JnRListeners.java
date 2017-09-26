package de.rexlmanu.lobby.jumpandrun;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.rexlmanu.lobby.methods.CoinsAPI;
import de.rexlmanu.lobby.methods.Message;
import de.rexlmanu.lobby.methods.Teleporter;
import de.rexlmanu.lobby.methods.Teleporter.TeleporterLocs;

public class JnRListeners implements Listener{

	 @SuppressWarnings("deprecation")
	@EventHandler
	  public void onMove(PlayerMoveEvent e)
	  {
	    Player p = e.getPlayer();
	    if (isDiamond(p))
	    {
	      p.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.spawn));
	      p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 6.0F, 8.0F);
	      p.playEffect(p.getLocation(), Effect.FIREWORKS_SPARK, 4);
	      p.sendMessage(" ");
	      p.sendMessage(Message.PREFIX.getValue()+"§7Du hast das JumpAndRun §ageschafft§7!");
	      p.sendMessage(Message.PREFIX.getValue()+"§a+§710 Coins");
	      p.sendMessage(" ");
	      CoinsAPI.addCoins(p.getUniqueId().toString(), 10);
	      updateScoreboard(p);
	    }
	  }
	  
	  public boolean isDiamond(Player p)
	  {
	    Location loc = p.getLocation();
	    if (loc.add(0.0D, -1.0D, 0.0D).getBlock().getType() == Material.DIAMOND_BLOCK) {
	      return true;
	    }
	    return false;
	  }
	  
	  public void updateScoreboard(Player p)
	  {
		  
	  }
	
}
