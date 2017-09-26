package de.rexlmanu.lobby.freundesystem;

import de.rexlmanu.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Methods {

	Main friends;

	private HashMap<String, Integer> pageindex = new HashMap<>();

	public Methods(Main friends) {
		this.friends = friends;
	}

	public Methods() {

	}

	public void loadFriendInventory(Player player, int page) {

		List<String> friendlist;
		if (page == 1) {
			friendlist = loadFriends(player, 0, 36);
		} else {
			friendlist = loadFriends(player, ((page - 1) * 36) + 1, page * 36);
		}

		pageindex.put(player.getName(), page);
		Inventory inventory = Bukkit.createInventory(null, 54,
				"§eFreundesliste");

		for (int fs = 0; fs < 36; fs++) {

			if (fs >= friendlist.size()) {
				inventory.setItem(fs, new ItemStack(Material.AIR));
			} else {
				String friend = friendlist.get(fs);
				ItemStack item = this.friends.getAPI().createHead(friend, null, "§8● §9" + friend);
				ItemMeta itemmeta = item.getItemMeta();
				ArrayList<String> lore = new ArrayList<>();
				if (this.friends.getFriendManager().getSetting(friend, "FcOnline")) {

					lore.add("§7Online auf §e" + get(friend, "Name", "FServer", "cFriends_Users"));
					itemmeta.setDisplayName("§8● §9" + friend);
					itemmeta.setLore(lore);

				} else {

					lore.add("§7Zul. Online vor §e"
							+ getLastTimeOnline((long) get(friend, "Name", "FConnect", "cFriends_Users")));
					itemmeta.setDisplayName("§8● §9" + friend);
					itemmeta.setLore(lore);

				}
				item.setItemMeta(itemmeta);
				inventory.setItem(fs, item);
			}
		}
		// Inventory

		inventory.setItem(36, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(37, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(38, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(39, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(40, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(41, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(42, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(43, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(44, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));

		// Navigation

		if (page != 1) {
			inventory.setItem(45, friends.getAPI().createItemwithMaterial(Material.ARROW, 0, 1, "§7« Zurück", null));
		} else {
			inventory.setItem(45, friends.getAPI().createItemwithMaterial(Material.BARRIER, 0, 1, "§c✖", null));
		}
		inventory.setItem(46, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(47,
				friends.getAPI().createItemwithMaterial(Material.NETHER_STAR, 0, 1, "§cEinstellungen", null));
		inventory.setItem(48, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));

		ArrayList<String> lore = new ArrayList<>();

		int size = getFriends(player.getName());
		if (size == 1) {
			lore.add("§7Du hast momentan §e" + 1 + " §7Freund!");
		} else {
			lore.add("§7Du hast momentan §e" + size + " §7Freunde!");
		}
		inventory.setItem(49, friends.getAPI().createHead(player.getName(), lore, "§8» §9" + player.getName()));
		inventory.setItem(50, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(51, friends.getAPI().createItemwithMaterial(Material.PAPER, 0, 1,
				"§eAnfragen §7(" + getRequests(player.getName()) + ")", null));
		inventory.setItem(52, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(53, friends.getAPI().createItemwithMaterial(Material.ARROW, 0, 1, "§7Vor »", null));

		player.openInventory(inventory);
	}

	public List<String> loadFriends(Player player, int from, int to) {

		HashMap<String, List<String>> f = getList(player.getName());
		List<String> friendlist = combine(f.get("online"), f.get("offline"));

		List<String> toreturn = new ArrayList<>();

		for (int i = from; i < to; i++) {
			if (friendlist.size() > i) {
				toreturn.add(friendlist.get(i));
			}
		}
		return toreturn;
	}

	public void loadOptions(Player player, String target){
		
		Inventory inventory = Bukkit.createInventory(null, 18, "§8● §9" + target);
		
		inventory.setItem(0, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		
		inventory.setItem(2, friends.getAPI().createItemwithMaterial(Material.CAKE, 0, 1, "§7in eine §5Party §7einladen", null));
		inventory.setItem(4, friends.getAPI().createItemwithMaterial(Material.DIAMOND_BOOTS, 0, 1, "§7zu deinem §2Freund §7springen", null));
		inventory.setItem(6, friends.getAPI().createItemwithMaterial(Material.FIREBALL, 0, 1, "§7von deiner §cFreundesliste §7entfernen", null));
		
		inventory.setItem(8, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(9, friends.getAPI().createHead(target, null, "§8● §9" + target));
		inventory.setItem(10, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(11, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(12, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(13, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(14, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(15, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(16, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(17, friends.getAPI().createItemwithMaterial(Material.ARROW, 0, 1, "§7« Zurück", null));
		
		
		player.openInventory(inventory);
	}
	
	public void loadRequestInventory(Player player){
		
		Inventory inventory = Bukkit.createInventory(null, 54, "§eAnfragen §7[" + getRequests(player.getName()) + "]");
		
		for(String request : getRequestList(player.getName())){
			
			String name = getNamebyUUID(request, "cFriends_Users");
			ArrayList<String> lore = new ArrayList<>();
			lore.add("§7Was möchtest du machen?");
			lore.add("§8- §7Anfrage §aannehmen§7(Linksklick");
			lore.add("§8- §7Anfrage §cablehnen§7(Rechtsklick");
			inventory.addItem(friends.getAPI().createHead(name, lore, "§8● §9" + name));
		}
		
		inventory.setItem(36, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(37, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(40, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(43, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(44, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		
		
		inventory.setItem(45, friends.getAPI().createItemwithMaterial(Material.NETHER_STAR, 0, 1, "§cEinstellungen", null));
		inventory.setItem(46, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(47, friends.getAPI().createItemwithID(351, 1, 1, "§cAlle ablehnen", null));
		inventory.setItem(48, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		
		ArrayList<String> lore = new ArrayList<>();
		int size = getRequests(player.getName());
		if(size == 1){
			lore.add("§7Du hast momentan §e1 §7Freundschaftsanfrage!");
		}else{
			lore.add("§7Du hast momentan §e" + size + " §7Freundschaftsanfragen!");
		}
		
		inventory.setItem(49, friends.getAPI().createHead(player.getName(), lore, "§9" + player.getName()));
		
		inventory.setItem(50, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(51, friends.getAPI().createItemwithID(351, 10, 1, "§aAlle annehmen", null));
		inventory.setItem(52, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(53, friends.getAPI().createItemwithMaterial(Material.IRON_DOOR, 0, 1, "§7« Zurück", null));
		
		player.openInventory(inventory);
	}
	
	public void loadSettingsInventory(Player player){
		
		Inventory inventory = Bukkit.createInventory(null, 27, "§9Einstellungen");
		
		inventory.setItem(0, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		
		ArrayList<String> lr = new ArrayList<>();
		lr.add("§7Stelle ein, ob du Freundschaftsanfragen");
		lr.add("§7erhalten möchtest.");
		ItemStack request = friends.getAPI().createItemwithMaterial(Material.BOOK, 0, 1, "§9Freundschaftsanfragen", null);
		inventory.setItem(2, request);
		
		ArrayList<String> lj = new ArrayList<>();
		lj.add("§7Stelle ein, ob deine Freunde zu dir springen können.");
		ItemStack jump = friends.getAPI().createItemwithMaterial(Material.BOOK, 0, 1, "§9Zu dir springen", null);
		inventory.setItem(3, jump);
		
		
		ArrayList<String> ln = new ArrayList<>();
		ln.add("§7Stelle ein, ob du Online bzw. Offline");
		ln.add("§7Nachrichten erhalten möchtest.");
		ItemStack online = friends.getAPI().createItemwithMaterial(Material.BOOK, 0, 1, "§9Online/Offline Nachrichten", null);
		inventory.setItem(5, online);
		
		ArrayList<String> ls = new ArrayList<>();
		ls.add("§7Stelle ein, ob du Server-Wechsel Nachrichten erhalten möchtest");
		ItemStack serverswitch = friends.getAPI().createItemwithMaterial(Material.BOOK, 0, 1, "§9Server-Wechsel Nachrichten", null);
		inventory.setItem(6, serverswitch);
	
		inventory.setItem(8, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(9, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		
		boolean rq = getSetting(player.getName(), "FRequest");
		boolean jmp = getSetting(player.getName(), "FJump");
		boolean onl = getSetting(player.getName(), "FOnline");
		boolean swtch = getSetting(player.getName(), "FSwitch");
		
		inventory.setItem(11, friends.getAPI().createItemwithID(351, (rq ? 10 : 1), 1, (rq ? "§a✔" : "§c✖"), lr));
		inventory.setItem(12, friends.getAPI().createItemwithID(351, (jmp ? 10 : 1), 1, (jmp ? "§a✔" : "§c✖"), lj));
		inventory.setItem(14, friends.getAPI().createItemwithID(351, (onl ? 10 : 1), 1, (onl ? "§a✔" : "§c✖"), ln));
		inventory.setItem(15, friends.getAPI().createItemwithID(351, (swtch ? 10 : 1), 1, (swtch ? "§a✔" : "§c✖"), ls));
		
		inventory.setItem(17, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(18, friends.getAPI().createHead(player.getName(), null, "§9" + player.getName() + "'s Einstellungen"));
		inventory.setItem(19, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(20, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(21, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(22, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(23, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(24, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(25, friends.getAPI().createItemwithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§3", null));
		inventory.setItem(26, friends.getAPI().createItemwithMaterial(Material.IRON_DOOR, 0, 1, "§7Zurück", null));
		
		player.openInventory(inventory);
	}
	
	public String getLastTimeOnline(long endmillis) {
		long current = System.currentTimeMillis();
		long end = endmillis;

		long millis = current - end;
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		long weeks = 0;
		long months = 0;
		long years = 0;

		while (millis > 1000) {
			millis -= 1000;
			seconds++;
		}

		while (seconds > 60) {
			seconds -= 60;
			minutes++;
		}

		while (minutes > 60) {
			minutes -= 60;
			hours++;
		}

		while (hours > 24) {
			hours -= 24;
			days++;
		}

		while (days > 7) {
			days -= 7;
			weeks++;
		}

		while (weeks > 4) {
			weeks -= 4;
			months++;
		}

		while (months > 12) {
			months -= 12;
			years++;
		}

		if (years != 0) {
			if (years == 1) {
				return "§e" + 1 + " Jahr";
			} else {
				return "§e" + years + " Jahre";
			}
		} else if (months != 0) {
			if (months == 1) {
				return "§e" + 1 + " Monat";
			} else {
				return "§e" + months + " Monate";
			}
		} else if (weeks != 0) {
			if (weeks == 1) {
				return "§e" + 1 + " Woche";
			} else {
				return "§e" + weeks + " Wochen";
			}
		} else if (days != 0) {
			if (days == 1) {
				return "§e" + 1 + " Tag";
			} else {
				return "§e" + days + " Tage";
			}
		} else if (hours != 0) {
			if (hours == 1) {
				return "§e" + 1 + " Stunde";
			} else {
				return "§e" + hours + " Stunden";
			}
		} else if (minutes != 0) {
			if (minutes == 1) {
				return "§e" + 1 + " Minute";
			} else {
				return "§e" + minutes + " Minuten";
			}
		} else if (seconds != 0) {
			if (seconds == 1) {
				return "§e" + 1 + " Sekunde";
			} else {
				return "§e" + seconds + " Sekunden";
			}
		} else {
			return "§cERROR CALCULATING DATA";
		}

	}

	public ArrayList<String> combine(List<String> first, List<String> second) {

		ArrayList<String> toreturn = new ArrayList<>();

		for (String entry : first) {
			toreturn.add(entry);
		}
		for (String entry : second) {
			toreturn.add(entry);
		}

		return toreturn;
	}

	public int getPage(Player player) {
		return pageindex.get(player.getName());
	}

	/* Settings */

	public boolean getSetting(String name, String type) {
		return Boolean.valueOf(String.valueOf(get(name, "Name", type, "cFriends_Users")));
	}

	@SuppressWarnings("static-access")
	public void setSetting(String name, String type, String value) {
		friends.getMysql().update("UPDATE cFriends_Users SET " + type + "='" + value + "' WHERE Name='" + name + "'");
	}

	/* MySQL */

	public HashMap<String, List<String>> getList(String name) {

		List<String> friendlist = getFriendList(name);
		List<String> fl = new ArrayList<>();

		for (String uuid : friendlist) {
			fl.add(getNamebyUUID(uuid, "cFriends_Users"));
		}

		List<String> offline = new ArrayList<>();
		List<String> online = new ArrayList<>();

		for (String entry : fl) {

			if (Bukkit.getPlayer(entry) != null) {
				online.add(entry);
			} else {
				offline.add(entry);
			}

		}

		Collections.sort(offline);
		Collections.sort(online);

		HashMap<String, List<String>> hash = new HashMap<>();
		hash.put("offline", offline);
		hash.put("online", online);

		return hash;
	}

	/* Friends - UUID1;UUID2;UUID3; */

	public String getFriendListRAW(String name) {
		return String.valueOf(get(name, "Name", "FList", "cFriends_Users"));
	}

	public List<String> getFriendList(String name) {
		String friendlist = getFriendListRAW(name);
		List<String> toreturn = new ArrayList<>();
		if (friendlist.isEmpty())
			return toreturn;
		String[] friends = friendlist.split(";");
		for (int i = 0; i < friends.length; i++) {
			toreturn.add(friends[i]);
		}
		return toreturn;
	}

	public int getFriends(String name) {
		String friendlist = getFriendListRAW(name);
		if (friendlist.isEmpty())
			return 0;
		String[] friends = friendlist.split(";");
		return friends.length;
	}

	/* Request - UUID1;UUID2;UUID3; */

	public String getRequestListRAW(String name) {
		return String.valueOf(get(name, "Name", "FRequests", "cFriends_Users"));
	}

	public List<String> getRequestList(String name) {
		String requestlist = getRequestListRAW(name);
		List<String> toreturn = new ArrayList<>();
		if (requestlist.isEmpty())
			return toreturn;
		String[] req = requestlist.split(";");
		for (int i = 0; i < req.length; i++) {
			toreturn.add(req[i]);
		}
		return toreturn;
	}

	public int getRequests(String name) {
		String requestlist = getRequestListRAW(name);
		if (requestlist.isEmpty())
			return 0;
		String[] req = requestlist.split(";");
		return req.length;
	}

	public String getUUIDbyName(String playername, String database) {
		String i = "";
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = friends.getMysql().getResult("SELECT * FROM " + database + " WHERE Name= '" + playername + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("UUID")) == null))
				;

			i = rs.getString("UUID");

		} catch (SQLException e) {

		}
		return i;
	}

	public String getNamebyUUID(String playername, String database) {
		String i = "";
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = friends.getMysql().getResult("SELECT * FROM " + database + " WHERE UUID= '" + playername + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("Name")) == null))
				;

			i = rs.getString("Name");

		} catch (SQLException e) {

		}
		return i;
	}

	public Object get(String whereresult, String where, String select, String database) {

		@SuppressWarnings("static-access")
		ResultSet rs = friends.getMysql().getResult("SELECT " + select + " FROM " + database + " WHERE " + where + "='" + whereresult + "'");
		try {
			if (rs.next()) {
				Object v = rs.getObject(select);
				return v;
			}
		} catch (SQLException e) {
			return "ERROR";
		}
		return "ERROR";
	}

}
