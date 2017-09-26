package de.rexlmanu.lobby.methods;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.rexlmanu.lobby.util.ItemBuilder;

public class Lobbywechsler {

	public static void open(Player p){
		Inventory inv = Bukkit.createInventory(null, 3*9, "§8Wähle eine Lobby aus.");
		
		int i = 0;
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
		do{
			inv.setItem(i, glass);
			i++;
		}while(i < 3*9);
		
//		List<String> servers = Main.cloudapi.getServers("Lobby");
//		String id = Main.cloudapi.getServerId();;


		Server lobby1 = new Server("127.0.0.1", 10001);
		Server lobby2 = new Server("127.0.0.1", 10002);
		lobby1.ping();
		lobby2.ping();


		if (lobby1.online == false){

			inv.setItem(1+9, offline("Lobby-1"));
		}else{
			inv.setItem(1+9, online("Lobby-1", lobby1.getOnlineplayers(), lobby1.getMaxplayers()));

		}
		if (lobby2.online == false){

			inv.setItem(2+9, offline("Lobby-2"));
		}else{
			inv.setItem(2+9, online("Lobby-2", lobby2.getOnlineplayers(), lobby2.getMaxplayers()));

		}


		inv.setItem(3+9, offline("Lobby-3"));
		inv.setItem(4+9, offline("Lobby-4"));
		inv.setItem(5+9, offline("Lobby-5"));
		inv.setItem(6+9, offline("Lobby-6"));
		inv.setItem(7+9, offline("Lobby-7"));
		
		//int slot = 0;
//		for(String servername : servers){
//			inv.setItem(slot+1+9, giveCorrectItem(servername, servername == id));
//			slot++;
//		}
		
		p.openInventory(inv);
	}
//	private static ItemStack giveCorrectItem(String servername, boolean isThisServer){
//		if(isThisServer){
//			return new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 5).setName("§8» §a"+servername).addEnchant(Enchantment.DURABILITY, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§r", "§7Auf dieser Lobby sind §a"+Main.cloudapi.getServerInfo(servername).getOnlineCount()+"§7 von "+Main.cloudapi.getServerInfo(servername).getMaxPlayers()+"§7 Spieler.", "§r").toItemStack();
//		}else{
//			return new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 5).setName("§8» §a"+servername).setLore("§r", "§7Auf dieser Lobby sind §a"+Main.cloudapi.getServerInfo(servername).getOnlineCount()+"§7 von "+Main.cloudapi.getServerInfo(servername).getMaxPlayers()+"§7 Spieler.", "§r").toItemStack();
//		}
//	}
	private static ItemStack online(String servername, int count, int maxcount){
		return new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 5).setName("§8» §a"+servername).setLore("§r", "§7Auf dieser Lobby sind §a"+count+"§7 von "+maxcount+"§7 Spieler.", "§r").toItemStack();
	}
	private static ItemStack offline(String servername){
			return new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14).setName("§8» §a"+servername).setLore("§r", "§cOffline§7...", "§r").toItemStack();
	}
	
}
