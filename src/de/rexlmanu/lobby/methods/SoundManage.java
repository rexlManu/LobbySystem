package de.rexlmanu.lobby.methods;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManage {

	public void sound(Sound sound,Player p){
		p.playSound(p.getLocation(), sound, 1, 1);
	}
}
