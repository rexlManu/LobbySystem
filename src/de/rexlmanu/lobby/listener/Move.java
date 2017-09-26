package de.rexlmanu.lobby.listener;

import de.rexlmanu.lobby.methods.Boots;
import de.rexlmanu.lobby.methods.Spuren;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class Move implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void move(PlayerMoveEvent e){
		Player p = e.getPlayer();
		
		Boots.setFunction(p);

		if(p.getLocation().getBlock().getType() == Material.GOLD_PLATE){
			Vector v = p.getLocation().getDirection().multiply(1.8D).setY(1.0D);
			p.setVelocity(v);
			p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 20, 1);
			p.playEffect(p.getLocation(), Effect.CLOUD, 50);
			p.playEffect(p.getLocation(), Effect.CLOUD, 30);
			p.playEffect(p.getLocation(), Effect.WATERDRIP, 100);
			p.playEffect(p.getLocation(), Effect.WATERDRIP, 54);
			p.playEffect(p.getLocation(), Effect.CLOUD, 64);
			p.playEffect(p.getLocation(), Effect.CLOUD, 46);
		}

		if(Spuren.currentSpuren.containsKey(p)){
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), 1);
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), new Random().nextInt(20));
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), 1);
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), 1);
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), new Random().nextInt(500));
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), 1);
			p.playEffect(p.getLocation(), Spuren.currentSpuren.get(p).getEffect(), new Random().nextInt(100));
		}
	}

}
