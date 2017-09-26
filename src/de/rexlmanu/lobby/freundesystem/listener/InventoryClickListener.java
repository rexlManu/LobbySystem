package de.rexlmanu.lobby.freundesystem.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.rexlmanu.lobby.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class InventoryClickListener implements Listener {

	Main friends;

	public InventoryClickListener(Main friends) {
		this.friends = friends;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickInventory(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();

		if (event.getCurrentItem() == null || event.getClickedInventory() == null)
			return;
		if(event.getCurrentItem().hasItemMeta() == false)
			return;

		if (event.getInventory().getName().startsWith("§eFreundesliste")) {
			event.setCancelled(true);
			if (event.getCurrentItem().getType() == Material.ARROW) {
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Vor »")) {
					friends.getFriendManager().loadFriendInventory(player,
							friends.getFriendManager().getPage(player) + 1);
				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7« Zurück")) {
					friends.getFriendManager().loadFriendInventory(player,
							friends.getFriendManager().getPage(player) - 1);
				}
			}else if(event.getCurrentItem().getType() == Material.NETHER_STAR){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				friends.getFriendManager().loadSettingsInventory(player);
			}else if(event.getCurrentItem().getType() == Material.PAPER){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				friends.getFriendManager().loadRequestInventory(player);
			}else if(event.getCurrentItem().getType() == Material.SKULL_ITEM){
				if(event.getCurrentItem().getItemMeta().getDisplayName().contains("»")){
					event.setCancelled(true);
					return;
				}
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				String target = event.getCurrentItem().getItemMeta().getDisplayName();
				target = target.replace("§8● §9", "");
				//if(target.equals(player.getName()))return;
				
				friends.getFriendManager().loadOptions(player, target);
				
			}
		}else if(event.getInventory().getName().startsWith("§9Einstellungen")){
			event.setCancelled(true);
			
			if(event.getCurrentItem().getType() == Material.IRON_DOOR){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				friends.getFriendManager().loadFriendInventory(player, 1);
				return;
			}
			if(event.getCurrentItem().getItemMeta().hasLore()){
				if(event.getCurrentItem().getItemMeta().getLore().get(0).startsWith("§7Stelle ein, ob du Freundschaftsanfragen")){
					player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
					boolean status = friends.getFriendManager().getSetting(player.getName(), "FRequest");
					if(status){
						friends.getFriendManager().setSetting(player.getName(), "FRequest", "false");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 1, 1, "§c✖", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Du erhälst nun keine Freundschaftsanfragen mehr!");
					}else{
						friends.getFriendManager().setSetting(player.getName(), "FRequest", "true");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 10, 1, "§a✔", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Du erhälst nun wieder Freundschaftsanfragen mehr!");
					}
					friends.getFriendManager().loadSettingsInventory(player);
					
				}else if(event.getCurrentItem().getItemMeta().getLore().get(0).startsWith("§7Stelle ein, ob deine Freunde")){
					player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
					boolean status = friends.getFriendManager().getSetting(player.getName(), "FJump");
					if(status){
						friends.getFriendManager().setSetting(player.getName(), "FJump", "false");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 1, 1, "§c✖", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Freunde können nun nicht mehr zu dir springen!");
					}else{
						friends.getFriendManager().setSetting(player.getName(), "FJump", "true");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 10, 1, "§a»✔", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Freunde können nun wieder zu dir springen!");
					}
					friends.getFriendManager().loadSettingsInventory(player);
					
				}else if(event.getCurrentItem().getItemMeta().getLore().get(0).startsWith("§7Stelle ein, ob du Online")){
					player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
					
					boolean status = friends.getFriendManager().getSetting(player.getName(), "FOnline");
					if(status){
						friends.getFriendManager().setSetting(player.getName(), "FOnline", "false");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 1, 1, "§c✖", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Du erhälst nun keine Online/Offline Nachrichten mehr!");
					}else{
						friends.getFriendManager().setSetting(player.getName(), "FOnline", "true");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 10, 1, "§a✔", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Du erhälst nun wieder Online/Offline Nachrichten mehr!");
					}
					friends.getFriendManager().loadSettingsInventory(player);
					
				}else if(event.getCurrentItem().getItemMeta().getLore().get(0).startsWith("§7Stelle ein, ob du Server-Wechsel")){
					player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
					
					boolean status = friends.getFriendManager().getSetting(player.getName(), "FSwitch");
					if(status){
						friends.getFriendManager().setSetting(player.getName(), "FSwitch", "false");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 1, 1, "§c✖", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Du erhälst nun keine Server-Wechsel Nachrichten mehr!");
					}else{
						friends.getFriendManager().setSetting(player.getName(), "FSwitch", "true");
						event.getInventory().setItem(event.getSlot(), friends.getAPI().createItemwithID(351, 10, 1, "§a✔", (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore()));
						player.sendMessage(friends.getPrefix() + "Du erhälst nun wieder Server-Wechsel Nachrichten mehr!");
					}
					friends.getFriendManager().loadSettingsInventory(player);
					
				}
			}
		}else if(event.getInventory().getName().startsWith("§eAnfragen")){
			event.setCancelled(true);
			if(event.getCurrentItem().getType() == Material.SKULL_ITEM){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				String target = event.getCurrentItem().getItemMeta().getDisplayName();
				target = target.replace("§8● §9", "");
				
				if(event.getClick().equals(ClickType.LEFT)){

					event.getInventory().removeItem(event.getCurrentItem());
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("friends");
					out.writeUTF("accept");
					out.writeUTF(target);
					player.sendPluginMessage(friends, "BungeeCord", out.toByteArray());
				}else if(event.getClick().equals(ClickType.RIGHT)){
					event.getInventory().removeItem(event.getCurrentItem());
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("friends");
					out.writeUTF("deny");
					out.writeUTF(target);
					player.sendPluginMessage(friends, "BungeeCord", out.toByteArray());
				}
				
			}else if(event.getCurrentItem().getType() == Material.NETHER_STAR){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				friends.getFriendManager().loadSettingsInventory(player);
			}else if(event.getCurrentItem().getTypeId() == 351){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				if(event.getCurrentItem().getData().getData() == 10){
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("friends");
					out.writeUTF("acceptall");
					out.writeUTF("");
					player.sendPluginMessage(friends, "BungeeCord", out.toByteArray());
				}else if(event.getCurrentItem().getData().getData() == 1){
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("friends");
					out.writeUTF("denyall");
					out.writeUTF("");
					player.sendPluginMessage(friends, "BungeeCord", out.toByteArray());
				}
				
			}else if(event.getCurrentItem().getType() == Material.IRON_DOOR){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				friends.getFriendManager().loadFriendInventory(player,
						friends.getFriendManager().getPage(player));
			}
			
		}else if(event.getInventory().getName().startsWith("§8● §9")){
			player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
			event.setCancelled(true);
			String target = event.getInventory().getName();
			target = target.replace("§8● §9", "");
			
			if(event.getCurrentItem().getType() == Material.DIAMOND_BOOTS){
				
				if(friends.getFriendManager().getSetting(target, "FcOnline")){
					boolean status = friends.getFriendManager().getSetting(player.getName(), "FJump");
					if(status){
						
						String server = String.valueOf(friends.getFriendManager().get(target, "Name", "FServer", "cFriends_Users"));
						
						Main.getInstance().sendToServer(player, target);
						player.sendMessage(friends.getPrefix() + "§7Du wirst mit §e" + server + " §7verbunden!");
						
					}else{
						player.sendMessage(friends.getPrefix() + "Du kannst zu diesem Spieler nicht springen!");
					}
				}else{
					player.sendMessage(friends.getPrefix() + "Dieser Spieler ist momentan nicht online!");
				}
				
			}else if(event.getCurrentItem().getType() == Material.CAKE){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				
				/* Party invite */
				
			}else if(event.getCurrentItem().getType() == Material.FIREBALL){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("friends");
				out.writeUTF("remove");
				out.writeUTF(target);
				player.sendPluginMessage(friends, "BungeeCord", out.toByteArray());
			}else if(event.getCurrentItem().getType() == Material.ARROW){
				player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
				friends.getFriendManager().loadFriendInventory(player, 1);
			}
			
		}

	}

}
