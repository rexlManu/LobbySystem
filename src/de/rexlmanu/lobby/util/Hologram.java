package de.rexlmanu.lobby.util;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rexlManu on 22.07.2017.
 * Plugin by rexlManu
 * https://rexlGames.de
 * Coded with IntelliJ
 */
public class Hologram
{
    private Location location;
    private List<String> lines;
    private double distance_above = -0.27D;
    private List<EntityArmorStand> armorstands = new ArrayList();

    public Hologram(Location loc, String... lines)
    {
        this.location = loc;
        this.lines = Arrays.asList(lines);
    }

    public Hologram(Location loc, List<String> lines)
    {
        this.location = loc;
        this.lines = ((ArrayList)lines);
    }

    public List<String> getLines()
    {
        return this.lines;
    }

    public Location getLocation()
    {
        return this.location;
    }

    public void send(Player p)
    {
        double y = getLocation().getY();
        for (int i = 0; i <= this.lines.size() - 1; i++)
        {
            y += this.distance_above;
            EntityArmorStand eas = getEntityArmorStand(y);
            eas.setCustomName((String)this.lines.get(i));
            display(p, eas);
            this.armorstands.add(eas);
        }
    }

    public void destroy(Player p)
    {
        for (EntityArmorStand eas : this.armorstands)
        {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { eas.getId() });
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void destroy()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            destroy(p);
        }
    }

    public void broadcast()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p);
        }
    }

    public void broadcast(List<Player> players)
    {
        for (Player p : players) {
            send(p);
        }
    }

    private EntityArmorStand getEntityArmorStand(double y)
    {
        WorldServer world = ((CraftWorld)getLocation().getWorld()).getHandle();
        EntityArmorStand eas = new EntityArmorStand(world);
        eas.setLocation(getLocation().getX(), y, getLocation().getZ(), 0.0F, 0.0F);
        eas.setInvisible(true);
        eas.setGravity(false);
        eas.setCustomNameVisible(true);
        return eas;
    }

    private void display(Player p, EntityArmorStand eas)
    {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(eas);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
}
