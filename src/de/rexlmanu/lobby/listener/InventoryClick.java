package de.rexlmanu.lobby.listener;

import de.rexlmanu.lobby.methods.*;
import de.rexlmanu.lobby.util.MessageAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.methods.Teleporter.TeleporterLocs;

public class InventoryClick implements Listener{
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent e){
		Player p = (Player)e.getWhoClicked();
		if(e.getCurrentItem() !=null){
			if(e.getCurrentItem().hasItemMeta()){
				String name = e.getCurrentItem().getItemMeta().getDisplayName();
				String guiname = e.getClickedInventory().getTitle();
				if(guiname.contains("§8Wähle eine Spur aus.")){
					if(Spuren.fromGuiNameToType(name) != null){
						if(Spuren.getSpuren(p.getUniqueId().toString()).contains(Spuren.fromGuiNameToType(name))){
							Spuren.putSpurenOn(p, Spuren.fromGuiNameToType(name));
							Main.sm.sound(Sound.NOTE_PLING, p);
							p.closeInventory();
						}else{
							Spuren.buyGUI(p, Spuren.fromGuiNameToType(name));
							Main.sm.sound(Sound.CLICK, p);
						}
					}else if(name.equals("§8» §cZurück")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}
				}else if(guiname.equals("§8Wähle eine Lobby aus.")){
					if(name.startsWith("§8»")){
						Main.sm.sound(Sound.CLICK, p);
						String servername = name.replace("§8» §a", "");
						Main.getInstance().sendToServer(p, servername);
						p.closeInventory();
					}
				}else if(guiname.contains("§8Wähle Boots aus.")){
						if(Boots.fromGuiNameToType(name) != null){
							if(Boots.getBoots(p.getUniqueId().toString()).contains(Boots.fromGuiNameToType(name))){
								Boots.putBootsOn(p, Boots.fromGuiNameToType(name));
								Main.sm.sound(Sound.NOTE_PLING, p);
								Boots.setFunction(p);
								Boots.setBoots(p);
								p.closeInventory();
							}else{
								Boots.buyGUI(p, Boots.fromGuiNameToType(name));
								Main.sm.sound(Sound.CLICK, p);
							}
						}else if(name.equals("§8» §cZurück")){
							Profil.openGUI(p);
							e.setCancelled(true);
							Main.sm.sound(Sound.CLICK, p);
						}
				}else if(guiname.contains("§8Wähle Gadgets aus.")){
					if(Gadgets.fromGuiNameToType(name) != null){
						if(Gadgets.getGadgets(p.getUniqueId().toString()).contains(Gadgets.fromGuiNameToType(name))){
							Gadgets.putGadgetsOn(p, Gadgets.fromGuiNameToType(name));
							Main.sm.sound(Sound.NOTE_PLING, p);
							Gadgets.setFunction(p);
							Gadgets.setGadgets(p);
							p.closeInventory();
						}else{
							Gadgets.buyGUI(p, Gadgets.fromGuiNameToType(name));
							Main.sm.sound(Sound.CLICK, p);
						}
					}else if(name.equals("§8» §cZurück")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}
				}else if(guiname.contains("§8Wähle Köpfe aus.")) {
					if (Heads.fromGuiNameToType(name) != null) {
						if (Heads.getHeads(p.getUniqueId().toString()).contains(Heads.fromGuiNameToType(name))) {
							Heads.putHeadsOn(p, Heads.fromGuiNameToType(name));
							Heads.setFunktion(p, Heads.fromGuiNameToType(name));
							Main.sm.sound(Sound.NOTE_PLING, p);
							Heads.setHeads(p);
							p.closeInventory();
						} else {
							Heads.buyGUI(p, Heads.fromGuiNameToType(name));
							Main.sm.sound(Sound.CLICK, p);
						}
					} else if (name.equals("§8» §cZurück")) {
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}
				}else if(guiname.equals("§8Deine persönlichen Stats.")){
					if(name.equals(Teleporter.warp2.getItemMeta().getDisplayName())){
						EigeneStats.openKnockiStats(p);
					}
				}else if(name.equals(Teleporter.warp1.getItemMeta().getDisplayName()) && e.getClickedInventory().getTitle().equals("§8Wähle einen Spielmodus aus.")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.warp1)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					MessageAPI.sendTitle(p,Teleporter.warp1.getItemMeta().getDisplayName(), "§3rexlGames.DE", 10);
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.warp1),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals(Teleporter.warp2.getItemMeta().getDisplayName()) && e.getClickedInventory().getTitle().equals("§8Wähle einen Spielmodus aus.")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.warp2)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					MessageAPI.sendTitle(p,Teleporter.warp2.getItemMeta().getDisplayName(), "§3rexlGames.DE", 10);
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.warp2),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals(Teleporter.warp3.getItemMeta().getDisplayName()) && e.getClickedInventory().getTitle().equals("§8Wähle einen Spielmodus aus.")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.warp3)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					MessageAPI.sendTitle(p,Teleporter.warp3.getItemMeta().getDisplayName(), "§3rexlGames.DE", 10);
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.warp3),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals(Teleporter.warp4.getItemMeta().getDisplayName()) && e.getClickedInventory().getTitle().equals("§8Wähle einen Spielmodus aus.")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.warp4)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					MessageAPI.sendTitle(p,Teleporter.warp4.getItemMeta().getDisplayName(), "§3rexlGames.DE", 10);
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.warp4),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.contains("Warp5")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.warp5)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.warp5),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.contains("Warp6")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.warp6)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.warp6),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.contains("Spawn")){
					if(!Teleporter.teleporterlocs.containsKey(TeleporterLocs.spawn)){
						p.sendMessage(Message.PREFIX.getValue()+"§7Diese Location wurde noch §cnicht §7gesetzt.");
						e.setCancelled(true);
						p.closeInventory();
						Main.sm.sound(Sound.NOTE_BASS_DRUM, p);
						return;
					}
					MessageAPI.sendTitle(p,Teleporter.spawn.getItemMeta().getDisplayName(), "§3rexlGames.DE", 10);
					Teleporter.teleport(Teleporter.teleporterlocs.get(TeleporterLocs.spawn),p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §4Spuren")){
					Spuren.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §3Boots")){
					Boots.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §aGadgets")){
					Gadgets.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §6Rangliste")){
					Rangliste.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §7Persönliche Stats")){
					EigeneStats.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §5Köpfe")){
					Heads.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §eGold-Rang")){
					Premiumkauf.open(p);
					e.setCancelled(true);
					Main.sm.sound(Sound.CLICK, p);
				}else if(name.equals("§8» §cZurück")){
					if(e.getClickedInventory().getTitle().equals("§8Wähle eine Spur aus.")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.startsWith("§8Kaufe§1§7: ")){
						Spuren.open(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.equals("§8Wähle einen Spielmodus aus.§r")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.equals("§8Deine persönlichen Stats.")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.equals("§8Kaufe§4§7: ")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.contains("§8Kaufe§7: ")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.equals("§8Wähle Köpfe aus.")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.contains("§8Kaufe§r§7: ")){
						Heads.open(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.contains("§8Goldkauf-Vorgang")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}
				}else if(name.startsWith("§8» §aKaufen §8× §7Preis: §c")){
					if(guiname.startsWith("§8Kaufe§1§7: ")){
						String spurenname = e.getClickedInventory().getItem(4+9+9).getItemMeta().getDisplayName();
						SpurenType sp = Spuren.fromGuiNameToType(spurenname);
						Spuren.buyConfirm(p, sp);
					}else if(guiname.startsWith("§8Kaufe§4§7: ")){
						String spurenname = e.getClickedInventory().getItem(4+9+9).getItemMeta().getDisplayName();
						BootsType sp = Boots.fromGuiNameToType(spurenname);
						Boots.buyConfirm(p, sp);
					}else if(guiname.startsWith("§8Kaufe§7: ")){
						String spurenname = e.getClickedInventory().getItem(4+9+9).getItemMeta().getDisplayName();
						GadgetsType sp = Gadgets.fromGuiNameToType(spurenname);
						Gadgets.buyConfirm(p, sp);
					}else if(guiname.startsWith("§8Kaufe§r§7: ")){
						String spurenname = e.getClickedInventory().getItem(4+9+9).getItemMeta().getDisplayName();
						HeadsType sp = Heads.fromGuiNameToType(spurenname);
						Heads.buyConfirm(p, sp);
					}else if(guiname.startsWith("§8Goldkauf-Vorgang")){
						Premiumkauf.confirmBuy(p);
					}
				}else if(name.startsWith("§8» §4Abrechen")){
					if(guiname.startsWith("§8Kaufe§1§7: ")){
						Spuren.open(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.startsWith("§8Kaufe§4§7: ")){
						Boots.open(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.startsWith("§8Kaufe§7: ")){
						Gadgets.open(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.startsWith("§8Kaufe§r§7: ")){
						Heads.open(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}else if(guiname.startsWith("§8Goldkauf-Vorgang")){
						Profil.openGUI(p);
						e.setCancelled(true);
						Main.sm.sound(Sound.CLICK, p);
					}
					
				}else{
					p.closeInventory();
				}
				if(BuildManager.canBuild(p)){
					e.setCancelled(false);
					}else{
						e.setCancelled(true);
				}
			}
		}
	}

}
