package de.rexlmanu.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Weather implements Listener{
	
	@EventHandler
	public void weather(WeatherChangeEvent e){
		e.getWorld().setFullTime(1);
		e.getWorld().setThundering(false);
		e.getWorld().setStorm(false);
		e.setCancelled(true);
	}

}
