package de.rexlmanu.lobby.methods;

import de.rexlmanu.lobby.knocki.StatsAPI;
import de.rexlmanu.lobby.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rexlManu on 24.07.2017.
 * Plugin by rexlManu
 * https://rexlGames.de
 * Coded with IntelliJ
 */
public class EigeneStats {

    public static void open(Player p){
        Inventory inv = Bukkit.createInventory(null, 5*9, "§8Deine persönlichen Stats.");

        int i = 0;
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
        do{
            inv.setItem(i, glass);
            i++;
        }while(i < 5*9);

        inv.setItem(2+9+9, editLore(Teleporter.warp2, "§r","§7Schaue dir deine persönlichen Stats an."));
        inv.setItem(1+9+9, editLore(Teleporter.warp1, "§r","§7Schaue dir deine persönlichen Stats an."));
        inv.setItem(6+9+9, editLore(Teleporter.warp3, "§r","§7Schaue dir deine persönlichen Stats an."));
        inv.setItem(7+9+9, editLore(Teleporter.warp4, "§r","§7Schaue dir deine persönlichen Stats an."));
        inv.setItem(4+9+9+9+9, new ItemBuilder(Material.IRON_DOOR, 1).setName("§8» §cZurück").toItemStack());

        p.openInventory(inv);

    }

    public static void openKnockiStats(Player p){
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§bKnocki§8-Stats");

        int i = 0;
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
        do{
            inv.setItem(i, glass);
            i++;
        }while(i < 4);

        StatsAPI statsAPI = StatsAPI.map.get(p);

        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) is.getItemMeta();
        sm.setOwner(p.getName());
        List<String> loreList = new ArrayList<>();
        loreList.add("§8§m---------------------");
        loreList.add("§r");
        loreList.add("§bDein Rank§7: "+statsAPI.getRank());
        loreList.add("§bDeine Kills§7: "+statsAPI.getKills());
        loreList.add("§bDeine Tode§7: "+statsAPI.getDeaths());
        loreList.add("§bDeine KD§7: "+statsAPI.getKd());
        loreList.add("§r§r");
        loreList.add("§8§m---------------------");
        sm.setLore(loreList);
        sm.setDisplayName("§7Deine §bKnocki§7-Stats");
        is.setItemMeta(sm);

        inv.setItem(2, is);
        p.openInventory(inv);
    }
    private static ItemStack editLore(ItemStack i, String... lore){
        ItemStack itemStack = i;
        ItemMeta meta = i.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
