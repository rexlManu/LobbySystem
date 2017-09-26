package de.rexlmanu.lobby.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FarmDestroyer implements Listener{
	
	  @SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR)
	  public void onTrample(PlayerInteractEvent event)
	  {
	    if (event.isCancelled()) {
	      return;
	    }
	    if (event.getAction() == Action.PHYSICAL)
	    {
	      Block block = event.getClickedBlock();
	      if (block == null) {
	        return;
	      }
	      int blockType = block.getTypeId();
	      if (blockType == Material.getMaterial(59).getId())
	      {
	        event.setCancelled(true);
	        
	        block.setTypeId(blockType);
	        block.setData(block.getData());
	      }
	    }
	    if (event.getAction() == Action.PHYSICAL)
	    {
	      Block block = event.getClickedBlock();
	      if (block == null) {
	        return;
	      }
	      int blockType = block.getTypeId();
	      if (blockType == Material.getMaterial(60).getId())
	      {
	        event.setCancelled(true);
	        
	        block.setType(Material.getMaterial(60));
	        block.setData(block.getData());
	      }
	    }
	  }

}
