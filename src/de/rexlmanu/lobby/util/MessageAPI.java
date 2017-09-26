package de.rexlmanu.lobby.util;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class MessageAPI {

	public static void sendActionbar(Player p,String msg){
	    IChatBaseComponent actionBar = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + msg + "\"}"));
	    PacketPlayOutChat actionb = new PacketPlayOutChat(actionBar, (byte)2);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(actionb);
	}
	public static void sendTitle(Player p,String head,String foot,int ticks){
	    IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + head + "\"}"));
	    IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + foot + "\"}"));
	    
	    PacketPlayOutTitle titre = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
	    PacketPlayOutTitle soustitre = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
	    
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(titre);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(soustitre);
	    
	    PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(time);
	}
	public static void sendTablist(Player p,String header,String footer){
	    CraftPlayer craftplayer = (CraftPlayer)p;
	    PlayerConnection connection = craftplayer.getHandle().playerConnection;
	    IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + header + "\"}"));
	    IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + footer + "\"}"));
	    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
	    try
	    {
	      Field headerField = packet.getClass().getDeclaredField("a");
	      headerField.setAccessible(true);
	      headerField.set(packet, headerJSON);
	      headerField.setAccessible(!headerField.isAccessible());
	      
	      Field footerField = packet.getClass().getDeclaredField("b");
	      footerField.setAccessible(true);
	      footerField.set(packet, footerJSON);
	      footerField.setAccessible(!footerField.isAccessible());
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    connection.sendPacket(packet);
	}
	public void sendClickMessage(Player player, String message, String hover, String cmdClick)
	{
		IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + message + "\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"" + hover + "\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + cmdClick + "\"}}]}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
}
