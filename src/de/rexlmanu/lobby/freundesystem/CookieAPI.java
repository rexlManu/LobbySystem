package de.rexlmanu.lobby.freundesystem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.rexlmanu.lobby.Main;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class CookieAPI {

	/*
	 * This class is written by CookieTV/Shorty. This contains many API's which
	 * allows you to develop your Plugins way easier. I hope you like it :)
	 * Activate Bungeecord in your main! Like this:
	 * this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord"); - not needed here
	 */
	Main friends;
	String cp;

	public CookieAPI(Main friends, String defaultplayercolor) {
		this.friends = friends;
		this.cp = defaultplayercolor;
		/* Here youve to implement your Main class for using Schedulers etc. */
	}

	/*
	 * 
	 * ItemStacks
	 * 
	 */

	public ItemStack createItemwithID(int id, int subid, int amount, String DisplayName, ArrayList<String> lore) {

		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(id, amount, (short) subid);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(DisplayName);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;

	}

	public ItemStack createItemwithMaterial(Material m, int subid, int amount, String DisplayName,
			ArrayList<String> lore) {
		ItemStack is = new ItemStack(m, amount, (short) subid);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(DisplayName);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;

	}

	public ItemStack createHead(String owner, ArrayList<String> lore) {

		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta sm = (SkullMeta) i.getItemMeta();
		sm.setOwner(owner);
		sm.setLore(lore);
		i.setItemMeta(sm);
		return i;
	}

	public ItemStack createHead(String owner, ArrayList<String> lore, String name) {

		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta sm = (SkullMeta) i.getItemMeta();
		sm.setOwner(owner);
		sm.setDisplayName(name);
		sm.setLore(lore);
		i.setItemMeta(sm);
		return i;
	}

	/*
	 * 
	 * Numbers & Formats
	 * 
	 */

	public boolean isNumeric(String number) {
		try {
			Integer.parseInt(number);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	public int getRandom(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public String getTime(int time) {

		int minutes = (int) time / 60;
		String min;
		if (minutes <= 9) {
			min = "0" + minutes;
		} else {
			min = "" + minutes;
		}

		int seconds = time - (minutes * 60);
		String sec;
		if (seconds <= 9) {
			sec = "0" + seconds;
		} else {
			sec = "" + seconds;
		}

		return min + ":" + sec;
	}

	public String getKD(int kills, int deaths, int splitafter) {

		if (kills == 0 && deaths == 0) {
			return "" + 0.00;
		} else if (kills >= 1 && deaths == 0) {
			return "" + ((double)kills);
		} else {
			if(splitafter != 0){
				String kd = "" + ((double)kills / deaths);
				if(kd.length() >= splitafter){
					kd = kd.substring(0, splitafter);
					return kd;
				}else{
					return "" + ((double)kills / deaths);
				}
			}else{
				return "" + ((double)kills / deaths);
			}
		}

	}

	public String getPercentof(int hundredpercent, int div, int splitafter) {

		String percent = "0.00%";

		if(hundredpercent == 0) return percent;
		
		double first = ((double)div/hundredpercent);
		double end = ((double)first * 100);
		
		percent = "" + end;

		if(splitafter != 0){
			String wl = percent;
			if(wl.length() >= splitafter){
				wl = wl.substring(0, splitafter);
				return wl + "%";
			}else{
				return percent + "%";
			}
		}else{
			return percent + "%";
		}
		
	}

	public List<String> convertStringArrayToArraylist(String str, String splitby) {
		List<String> strFragments = new ArrayList<>();
		String[] strArray = str.split(splitby);

		for (int i = 0; i < strArray.length; i++) {
			strFragments.add(strArray[i]);
		}
		return strFragments;
	}

	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	public int getRemainingSeconds(long currenttimemillis, long timemillisbefore) {
		long millis = timemillisbefore - currenttimemillis;
		int seconds = 0;
		while (millis > 1000) {
			millis -= 1000;
			seconds++;
		}
		return seconds;
	}

	public int getRemainingMinutes(int seconds) {
		int minutes = 0;
		while (seconds > 60) {
			seconds -= 60;
			minutes++;
		}
		return minutes;
	}

	public int getRemainingHours(int minutes) {
		int hours = 0;
		while (minutes > 60) {
			minutes -= 60;
			hours++;
		}
		return hours;
	}

	public int getRemainingDays(int hours) {
		int days = 0;
		while (hours > 24) {
			hours -= 24;
			days++;
		}
		return days;
	}

	public int getRemainingWeeks(int days) {
		int weeks = 0;
		while (days > 7) {
			days -= 7;
			weeks++;
		}
		return weeks;
	}

	public String getRemainingTime(long start, long timemillisbefore, String nullresult, String color) {
		long current = start;
		long end = timemillisbefore;
		if (end == -1) {
			return nullresult;
		}
		long millis = end - current;
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		long weeks = 0;

		while (millis > 1000) {
			millis -= 1000;
			seconds++;
		}
		while (seconds > 60) {
			seconds -= 60;
			minutes++;
		}
		while (minutes > 60) {
			seconds -= 60;
			hours++;
		}
		while (hours > 24) {
			seconds -= 24;
			days++;
		}
		while (days > 7) {
			days -= 7;
			weeks++;
		}

		return color + "" + weeks + " Woche(n) " + days + " Tag(e) " + hours + " Stunde(n) " + minutes + " Minute(n) "
				+ seconds + " Sekunde(n)";
	}

	public String getRemainingHours(long currenttimemillis, long timemillisbefore, String nullresult, String color) {
		long current = currenttimemillis;
		long end = timemillisbefore;
		if (end == -1) {
			return nullresult;
		}
		long millis = end - current;
		long seconds = 0;
		long minutes = 0;
		long hours = 0;

		while (millis > 1000) {
			millis -= 1000;
			seconds++;
		}
		while (seconds > 60) {
			seconds -= 60;
			minutes++;
		}
		while (minutes > 60) {
			seconds -= 60;
			hours++;
		}

		return color + "" + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
	}

	/*
	 * 
	 * Players
	 * 
	 */

	public void refreshPlayer(Player player) {
		player.setExp(0);
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setExp(0.0F);
		player.setLevel(0);
		player.setFireTicks(0);
		player.setGameMode(GameMode.SURVIVAL);

		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	public void removePotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	public Player getRandomOnlinePlayer() {

		List<Player> list = new ArrayList<>();

		for (Player player : Bukkit.getOnlinePlayers()) {
			list.add(player);
		}
		int rn = getRandom(list.size());

		return list.get(rn);
	}

	public String getRandomString(List<String> list) {
		int rn = getRandom(list.size());
		return list.get(rn);
	}

	public Player getRandomPlayer(List<Player> list) {
		int rn = getRandom(list.size());
		return list.get(rn);
	}

	public Object getRandomObject(List<Object> list) {
		int rn = getRandom(list.size());
		return list.get(rn);
	}

	public String getPlayerName(Player player) {
		return cp + player.getDisplayName();
	}

	public void connect(Player player, String servername) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(servername);
		player.sendPluginMessage(friends, "BungeeCord", out.toByteArray());
	}

	public void hidePlayer(Player player) {
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			player2.hidePlayer(player);
		}
	}

	public void showPlayer(Player player) {
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			player2.showPlayer(player);
		}
	}

	public void hidePlayertoIngames(Player player, List<Player> spectators) {
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (spectators.contains(player2) == false) {
				player2.hidePlayer(player);
			}
		}
	}

	public void showPlayertoIngames(Player player, List<Player> spectators) {
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (spectators.contains(player2) == false) {
				player2.showPlayer(player);
			}
		}
	}

	/*
	 * 
	 * Entitys
	 * 
	 */

	public static void findPath(LivingEntity e, Location l, float speed) {
		EntityInsentient ei = ((EntityInsentient) ((CraftEntity) e).getHandle());
		Navigation nav = (Navigation) ei.getNavigation();
		nav.a(true);
		PathEntity path = nav.a(l.getX(), l.getY(), l.getZ());
		nav.a(path, speed);
	}

	public void freezeEntity(Entity en) {
		net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEn.c(compound);
		compound.setByte("NoAI", (byte) 1);
		nmsEn.f(compound);
	}


	/*
	 * 
	 * FileManager
	 * 
	 */

	public void createNewFile(String filename, String path) {

		File file = new File("plugins/" + path, filename);
		if (file.exists())
			return;
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getFile(String filename, String path) {

		File file = new File("plugins/" + path, filename);
		return file;

	}

	public void deleteFile(String filename, String path) {

		File file = new File("plugins/" + path, filename);
		file.delete();

	}

	public FileConfiguration getConfiguration(String filename, String path) {
		return YamlConfiguration.loadConfiguration(getFile(filename, path));
	}

	/*
	 * 
	 * Location API
	 * 
	 */

	public Location getLocation(String ymlname, String name, String path) {

		FileConfiguration cfg = getConfiguration(ymlname, path);
		double x = cfg.getDouble(name + ".X");
		double y = cfg.getDouble(name + ".Y");
		double z = cfg.getDouble(name + ".Z");
		double yaw = cfg.getDouble(name + ".Yaw");
		double pitch = cfg.getDouble(name + ".Pitch");
		String world = cfg.getString(name + ".World");

		Location l = new Location(Bukkit.getWorld(world), x, y, z);
		l.setYaw((float) yaw);
		l.setPitch((float) pitch);
		return l;
	}

	public void saveLocation(Player p, String ymlname, String name, String path) {

		FileConfiguration cfg = getConfiguration(ymlname, path);

		cfg.set(name + ".X", p.getLocation().getX());
		cfg.set(name + ".Y", p.getLocation().getY());
		cfg.set(name + ".Z", p.getLocation().getZ());
		cfg.set(name + ".Yaw", p.getLocation().getYaw());
		cfg.set(name + ".Pitch", p.getLocation().getPitch());
		cfg.set(name + ".World", p.getWorld().getName());

		try {
			cfg.save(getFile(ymlname, path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * Cool Things
	 * 
	 */

	public FlyingItem spawnFlyingItem(ItemStack item, Location location) {

		FlyingItem fly = new FlyingItem();

		fly.setLocation(location);
		fly.setHeight(2.25);
		fly.setItemStack(item);
		fly.spawn();

		return fly;
	}

	/*
	 * 
	 * Packets
	 * 
	 */

	public void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") })
					.invoke(playerConnection, new Object[] { packet });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendTitle(Player player, String title, String subtitle) {
		int fadeIn = 1;
		int stay = 1;
		int fadeOut = 1;
		try {
			if (title != null) {
				title = ChatColor.translateAlternateColorCodes('&', title);
				title = title.replaceAll("%p%", player.getDisplayName());
				Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE")
						.get(null);
				Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
				Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				Object titlePacket = titleConstructor
						.newInstance(new Object[] { enumTitle, chatTitle, fadeIn, stay, fadeOut });
				sendPacket(player, titlePacket);
			}
			if (subtitle != null) {
				subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
				subtitle = subtitle.replaceAll("%p%", player.getDisplayName());
				Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE")
						.get(null);
				Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
				Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				Object subtitlePacket = subtitleConstructor
						.newInstance(new Object[] { enumSubtitle, chatSubtitle, fadeIn, stay, fadeOut });
				sendPacket(player, subtitlePacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendActionbar(String msg, Player player) {
		msg = msg.replace("&", "»");
		msg = msg.replace("%p", player.getDisplayName());

		PlayerConnection con = ((CraftPlayer) player).getHandle().playerConnection;

		IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);
		con.sendPacket(packet);
	}

	public void sendTabTitle(Player player, String header, String footer) {
		if (header == null)
			header = "";

		if (footer == null)
			footer = "";

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");

		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");

		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

		try {

			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			connection.sendPacket(headerPacket);

		}
	}
	HashMap<Player, Hologram> holo = new HashMap<>();
	public void setHolo(Player player, String[] text, Location loc, boolean clearlast){
		Hologram hologram = new Hologram(text, loc);
		hologram.showPlayer(player);
		if(clearlast){
			if(holo.containsKey(player)){
				Hologram holotoclear = holo.get(player);
				holotoclear.hidePlayer(player);
				holo.remove(player);
			}
		}
		holo.put(player, hologram);
	}
	public void removeHolo(Player player){
		if(holo.containsKey(player)){
			Hologram hologram = holo.get(player);
			hologram.hidePlayer(player);
			holo.remove(player);
		}
	}

}

class FlyingItem {

	private ArmorStand armorstand;
	private Location location;
	private String text = null;
	private Boolean h = false;
	private ItemStack itemstack;
	private double height = -1.3;

	public FlyingItem() {

	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setItemStack(ItemStack itemstack) {
		this.itemstack = itemstack;
	}

	public void setHeight(double height) {
		this.height = height - 1.3;
		if (this.location != null) {
			this.location.setY(this.location.getY() + this.height);
			h = true;
		}
	}

	public void remove() {
		this.location = null;
		this.armorstand.remove();
		this.armorstand.getPassenger().remove();
		this.armorstand = null;
		this.h = false;
		this.height = 0;
		this.text = null;
		this.itemstack = null;
	}

	public void teleport(Location location) {
		if (this.location != null) {
			armorstand.teleport(location);
			this.location = location;
		}
	}

	public void spawn() {
		if (!h) {
			this.location.setY(this.location.getY() + this.height);
			h = true;
		}
		armorstand = (ArmorStand) this.location.getWorld().spawn(this.location, ArmorStand.class);
		armorstand.setGravity(false);
		armorstand.setVisible(false);
		Item i = this.location.getWorld().dropItem(this.getLocation().add(0, 2, 0), this.itemstack);
		i.setPickupDelay(2147483647);
		if (this.text != null) {
			i.setCustomName(this.text);
			i.setCustomNameVisible(true);
		}
		armorstand.setPassenger(i);
	}

	public Location getLocation() {
		return this.location;
	}

	public ItemStack getItemStack() {
		return this.itemstack;
	}

	public double getHeight() {
		return this.height;
	}

	public String getText() {
		return this.text;
	}
}

class Hologram {

	private java.util.List<EntityArmorStand> List = new ArrayList<EntityArmorStand>();
	private String[] text;
	private Location loc;
	private double distance = 0.25D;
	int count;

	public Hologram(String[] text, Location loc) {

		this.text = text;
		this.loc = loc;
		create();
	}

	public void create() {
		for (String text : this.text) {
			EntityArmorStand e = new EntityArmorStand(((CraftWorld) this.loc.getWorld()).getHandle(), this.loc.getX(),
					this.loc.getY(), this.loc.getZ());

			e.setCustomName(text);
			e.setCustomNameVisible(true);
			e.setInvisible(true);
			e.setGravity(true);
			List.add(e);
			count++;
			this.loc.subtract(0, this.distance, 0);

		}
		for (int i = 0; i < count; i++) {
			this.loc.add(0, this.distance, 0);
		}
		this.count++;
	}

	public void hidePlayer(Player p) {
		for (EntityArmorStand a : List) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(a.getId());
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

		}

	}

	public void showPlayer(Player p) {
		for (EntityArmorStand a : List) {
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(a);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

		}

	}

}

class Particle {

	EnumParticle particletype;
	boolean longdistance;
	Location location;
	float offsetx;
	float offsety;
	float offsetz;
	float speed;
	int amount;

	public Particle(EnumParticle particletype, Location location, boolean longdistance, float offsetx, float offsety,
			float offsetz, float speed, int amount) {
		this.particletype = particletype;
		this.location = location;
		this.longdistance = longdistance;
		this.offsetx = offsetx;
		this.offsety = offsety;
		this.offsetz = offsetz;
		this.speed = speed;
		this.amount = amount;
	}

	public void sendAll() {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(this.particletype, this.longdistance,
				(float) this.location.getX(), (float) this.location.getY(), (float) this.location.getZ(), this.offsetx,
				this.offsety, this.offsetz, this.speed, this.amount, 0);

		for (Player player : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void sendPlayer(Player player) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(this.particletype, this.longdistance,
				(float) this.location.getX(), (float) this.location.getY(), (float) this.location.getZ(), this.offsetx,
				this.offsety, this.offsetz, this.speed, this.amount);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}

class API_Form {
	public void playEffectWithForm(Player p, Location loc, ParticleType type, Form form, double length, double update,
			float speed) {
		Location middle = loc.clone();
		Location myLoc = loc.clone();
		Location before = myLoc.clone();
		if (form == Form.CIRCLE) {

			for (double x = before.getX() - length - 0.25D; x < before.getX() + length + 0.25D; x += update) {
				for (double z = before.getZ() - length - 0.25D; z < before.getZ() + length + 0.25D; z += update) {
					myLoc.setX(x);
					myLoc.setZ(z);
					if ((middle.distance(myLoc) > length - update / 2.0D)
							&& (middle.distance(myLoc) < length + update / 2.0D)) {
						playEffect(myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
					}
				}
			}
		} else if (form == Form.SPHERE) {
			for (double x = before.getX() - length - 0.25D; x < before.getX() + length + 0.25D; x += update) {
				for (double y = before.getY() - length - 0.25D; y < before.getY() + length + 0.25D; y += update) {
					for (double z = before.getZ() - length - 0.25D; z < before.getZ() + length + 0.25D; z += update) {
						myLoc.setX(x);
						myLoc.setY(y);
						myLoc.setZ(z);
						if ((middle.distance(myLoc) > length - update / 2.0D)
								&& (middle.distance(myLoc) < length + update / 2.0D)) {
							playEffect(myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
						}
					}
				}
			}
		} else if (form == Form.SQUARE) {
			myLoc.setZ(before.getZ() - length / 2.0D);
			for (double x = before.getX() - length / 2.0D; x < before.getX() + length / 2.0D; x += update) {
				myLoc.setX(x);

				playEffect(myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
			}
			myLoc.setZ(before.getZ() + length / 2.0D);
			for (double x = before.getX() - length / 2.0D; x < before.getX() + length / 2.0D; x += update) {
				myLoc.setX(x);

				playEffect(myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
			}
			myLoc.setZ(before.getZ());
			myLoc.setX(before.getX() - length / 2.0D);
			for (double z = before.getZ() - length / 2.0D; z < before.getZ() + length / 2.0D; z += update) {
				myLoc.setZ(z);

				playEffect(myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
			}
			myLoc.setX(before.getX() + length / 2.0D);
			for (double z = before.getZ() - length / 2.0D; z < before.getZ() + length / 2.0D; z += update) {
				myLoc.setZ(z);

				playEffect(myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
			}
		} else if (form == Form.CUBE) {
			for (double y = before.getY() - length / 2.0D; y < before.getY() + length / 2.0D; y += update) {
				myLoc.setY(y);
				playEffectWithForm(p, myLoc, type, Form.SQUARE, length, update, speed);
			}
			myLoc.setY(before.getY() - length / 2.0D);
			for (double x = before.getX() - length / 2.0D; x < before.getX() + length / 2.0D; x += update) {
				for (double z = before.getZ() - length / 2.0D; z < before.getZ() + length / 2.0D; z += update) {
					myLoc.setX(x);
					myLoc.setZ(z);
					playEffect(p, myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
				}
			}
			myLoc.setY(before.getY() + length / 2.0D);
			for (double x = before.getX() - length / 2.0D; x < before.getX() + length / 2.0D; x += update) {
				for (double z = before.getZ() - length / 2.0D; z < before.getZ() + length / 2.0D; z += update) {
					myLoc.setX(x);
					myLoc.setZ(z);
					playEffect(p, myLoc, type, 0.0F, 0.0F, 0.0F, speed, 1);
				}
			}
		}
	}

	public void playEffectWithForm(Location loc, ParticleType type, Form form, double length, double update,
			float speed) {
		for (Player p : loc.getWorld().getPlayers()) {
			playEffectWithForm(p, loc, type, form, length, update, speed);
		}
	}

	public void playEffect(Player p, Location loc, ParticleType type, float offX, float offY, float offZ, float speed,
			int amount) {
		sendPacket(p, getWorldParticlesPacket(type.getHandle(), (float) loc.getX(), (float) loc.getY(),
				(float) loc.getZ(), offX, offY, offZ, speed, amount));
	}

	public void playEffect(Location loc, ParticleType type, float offX, float offY, float offZ, float speed,
			int amount) {
		for (Player p : loc.getWorld().getPlayers()) {
			playEffect(p, loc, type, offX, offY, offZ, speed, amount);
		}
	}

	public enum Form {
		CIRCLE, SPHERE, SQUARE, CUBE;
	}

	public enum ParticleType {
		BARRIER(EnumParticle.BARRIER), BLOCK_CRACK(EnumParticle.BLOCK_CRACK), BLOCK_DUST(
				EnumParticle.BLOCK_DUST), CLOUD(EnumParticle.CLOUD), CRIT(EnumParticle.CRIT), CRIT_MAGIC(
						EnumParticle.CRIT_MAGIC), DRIP_LAVA(EnumParticle.DRIP_LAVA), DRIP_WATER(
								EnumParticle.DRIP_WATER), ENCHANTMENT_TABLE(
										EnumParticle.ENCHANTMENT_TABLE), EXPLOSION_HUGE(
												EnumParticle.EXPLOSION_HUGE), EXPLOSION_LARGE(
														EnumParticle.EXPLOSION_LARGE), EXPLOSION_NORMAL(
																EnumParticle.EXPLOSION_NORMAL), FIREWORKS_SPARK(
																		EnumParticle.FIREWORKS_SPARK), FLAME(
																				EnumParticle.FLAME), FOOTSTEP(
																						EnumParticle.FOOTSTEP), HEART(
																								EnumParticle.HEART), ITEM_CRACK(
																										EnumParticle.ITEM_CRACK), ITEM_TAKE(
																												EnumParticle.ITEM_TAKE), LAVA(
																														EnumParticle.LAVA), MOB_APPEARANCE(
																																EnumParticle.MOB_APPEARANCE), NOTE(
																																		EnumParticle.NOTE), PORTAL(
																																				EnumParticle.PORTAL), REDSTONE(
																																						EnumParticle.REDSTONE), SLIME(
																																								EnumParticle.SLIME), SMOKE_LARGE(
																																										EnumParticle.SMOKE_LARGE), SMOKE_NORMAL(
																																												EnumParticle.SMOKE_NORMAL), SNOW_SHOVEL(
																																														EnumParticle.SNOW_SHOVEL), SNOWBALL(
																																																EnumParticle.SNOWBALL), SPELL(
																																																		EnumParticle.SPELL), SPELL_INSTANT(
																																																				EnumParticle.SPELL_INSTANT), SPELL_MOB(
																																																						EnumParticle.SPELL_MOB), SPELL_MOB_AMBIENT(
																																																								EnumParticle.SPELL_MOB_AMBIENT), SPELL_WITCH(
																																																										EnumParticle.SPELL_WITCH), SUSPENDED(
																																																												EnumParticle.SUSPENDED), SUSPENDED_DEPTH(
																																																														EnumParticle.SUSPENDED_DEPTH), TOWN_AURA(
																																																																EnumParticle.TOWN_AURA), VILLAGER_ANGRY(
																																																																		EnumParticle.VILLAGER_ANGRY), VILLAGER_HAPPY(
																																																																				EnumParticle.VILLAGER_HAPPY), WATER_BUBBLE(
																																																																						EnumParticle.WATER_BUBBLE), WATER_DROP(
																																																																								EnumParticle.WATER_DROP), WATER_SPLASH(
																																																																										EnumParticle.WATER_SPLASH), WATER_WAKE(
																																																																												EnumParticle.WATER_WAKE);

		private EnumParticle handle;

		private ParticleType(EnumParticle handle) {
			this.handle = handle;
		}

		public EnumParticle getHandle() {
			return this.handle;
		}
	}

	private static void sendPacket(Player player, Packet<?> packet) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		entityPlayer.playerConnection.sendPacket(packet);
	}

	private static PacketPlayOutWorldParticles getWorldParticlesPacket(EnumParticle particle, float x, float y, float z,
			float offX, float offY, float offZ, float speed, int amount) {
		return new PacketPlayOutWorldParticles(particle, false, x, y, z, offX, offY, offZ, speed, amount, null);
	}
}