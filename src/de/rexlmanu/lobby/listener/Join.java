package de.rexlmanu.lobby.listener;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.knocki.StatsAPI;
import de.rexlmanu.lobby.methods.*;
import de.rexlmanu.lobby.methods.Teleporter.TeleporterLocs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;

public class Join implements Listener{

	@EventHandler
	public void join(PlayerJoinEvent e){
		Player p = e.getPlayer();
		p.getUniqueId();
		e.setJoinMessage(null);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		try {
			ScoreboardManage.setBoard(p);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		new BukkitRunnable() {
			@Override
			public void run() {

				try {
					OfflineSupport.isExisting(p.getUniqueId().toString(), p.getName());
					OfflineSupport.update(p.getName(), p.getUniqueId().toString());
				} catch (SQLException e1) {
				}

				RangManage.isExisting(p.getUniqueId().toString().replace("-", ""));
				RangManage.loadRang(p);

				StatsAPI.map.put(p, new StatsAPI(p));
			}
		}.runTaskAsynchronously(Main.instance);

		LobbyItems.giveItems(p);
		p.setGameMode(GameMode.ADVENTURE);
		p.setLevel(2017);
		p.setFoodLevel(20);
		p.resetMaxHealth();
		p.setPlayerTime(0, false);
		p.setHealthScale(6);
		p.setMaxHealth(6);
		p.setHealth(6);
		p.removePotionEffect(PotionEffectType.SPEED);
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
		p.removePotionEffect(PotionEffectType.JUMP);
		if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.spawn)){
			p.sendMessage(Message.PREFIX.getValue()+"§7Der Spawn wurde noch §cnicht §7gesetzt.");
		}else{
			p.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.spawn));
		}

		if(!Playerhider.playerExists(p.getUniqueId().toString().replace("-", ""))){
			new BukkitRunnable() {
				@Override
				public void run() {
					Playerhider.createPlayer(p.getUniqueId().toString().replace("-", ""));
				}
			}.runTaskAsynchronously(Main.instance);
		}


		Playerhider.playerHiderSettings.put(p, Playerhider.getHiderSettings(p.getUniqueId().toString().replace("-", "")));
		Playerhider.setFunktion(p, Playerhider.playerHiderSettings.get(p));
		Playerhider.giveItem(p);
		Vector v = p.getLocation().getDirection().multiply(0.0D).setY(0.8D);
		p.setVelocity(v);
		
		Main.sm.sound(Sound.LEVEL_UP, p);
		for(Player all:Bukkit.getOnlinePlayers()){
			TablistManage.sendTablist(all);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				CoinsAPI.isExisting(p.getUniqueId().toString());

			}
		}.runTaskAsynchronously(Main.instance);

		Spuren.addToHashmap(p);

		
	}

}
