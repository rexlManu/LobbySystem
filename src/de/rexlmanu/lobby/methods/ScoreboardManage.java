package de.rexlmanu.lobby.methods;

import java.sql.SQLException;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;

public class ScoreboardManage {

	public static void setBoard(Player p) throws SQLException{
		
		net.minecraft.server.v1_8_R3.Scoreboard sb = new net.minecraft.server.v1_8_R3.Scoreboard();
		ScoreboardObjective obj = sb.registerObjective("Lobby", IScoreboardCriteria.b);
		obj.setDisplayName("§3rexlGames.DE");
		PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
		PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

		ScoreboardScore a1 = new ScoreboardScore(sb, obj, "§8§m----------------");
		ScoreboardScore a2 = new ScoreboardScore(sb, obj, "§6➲ §9Dein Rank");
		ScoreboardScore a3 = new ScoreboardScore(sb, obj, "§8➥ §7"+new RangManage(p).getRang().getPrefix());
		ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§r§a");
		ScoreboardScore a5 = new ScoreboardScore(sb, obj, "§6✪ §9Deine Coins");
		ScoreboardScore a6 = new ScoreboardScore(sb, obj, "§8➥ §7"+CoinsAPI.getCoins(p.getUniqueId().toString()));
		ScoreboardScore a7 = new ScoreboardScore(sb, obj, "§r§a§6");
		ScoreboardScore a12 = new ScoreboardScore(sb, obj, "§6✸ §9TeamSpeak");
		ScoreboardScore a13 = new ScoreboardScore(sb, obj, "§8➥ §7ts.rexlGames.de");
		ScoreboardScore a14 = new ScoreboardScore(sb, obj, "§r§8§m----------------");

		a1.setScore(9);
		a2.setScore(8);
		a3.setScore(7);
		a4.setScore(6);
		a5.setScore(5);
		a6.setScore(4);
		a7.setScore(3);;
		a12.setScore(2);
		a13.setScore(1);
		a14.setScore(0);

		PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
		PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a1);
		PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
		PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
		PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
		PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(a5);
		PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(a6);
		PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(a7);
		PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(a12);
		PacketPlayOutScoreboardScore pa13 = new PacketPlayOutScoreboardScore(a13);
		PacketPlayOutScoreboardScore pa14 = new PacketPlayOutScoreboardScore(a14);
		
		sendPacket(p, removePacket);
		sendPacket(p, createPacket);
		sendPacket(p, display);

		sendPacket(p, pa1);
		sendPacket(p, pa2);
		sendPacket(p, pa3);
		sendPacket(p, pa4);
		sendPacket(p, pa5);
		sendPacket(p, pa6);
		sendPacket(p, pa7);
		sendPacket(p, pa12);
		sendPacket(p, pa13);
		sendPacket(p, pa14);

	}

	@SuppressWarnings("rawtypes")
	private static void sendPacket(Player p, Packet packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	
}
