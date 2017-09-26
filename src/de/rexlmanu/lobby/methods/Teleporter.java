package de.rexlmanu.lobby.methods;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.util.ConfigManager;
import de.rexlmanu.lobby.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Teleporter {

    public static ItemStack spawn = new ItemBuilder(Material.GLOWSTONE_DUST, 1).setName("§8» §e§lSpawn").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §e§lSpawn", "§8➥ §7Teleportiere dich zum Spawn.", "§r", "§8§m-------------------------------------").toItemStack();
    public static ItemStack warp1 = new ItemBuilder(Material.GRASS, 1).setName("§8» §2§lSkyWars").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §2§lSkyWars", "§8➥ §7Loote Kisten und baue dich zu den anderen Inseln.", "§r", "§8§m-------------------------------------").toItemStack();
    public static ItemStack warp2 = new ItemBuilder(Material.FEATHER, 1).setName("§8» §b§lKnocki").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §b§lKnocki", "§8➥ §7Booste die anderen Spieler weg.", "§r", "§8§m-------------------------------------").toItemStack();
    public static ItemStack warp3 = new ItemBuilder(Material.STICK, 1).setName("§8» §4§lTTT").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §4§lTTT", "§8➥ §7Vertraust du ihn, oder vertraut er dir?", "§r", "§8§m-------------------------------------").toItemStack();
    public static ItemStack warp4 = new ItemBuilder(Material.SANDSTONE, 1).setName("§8» §eOneLine").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §e§lOneLine", "§8➥ §7Versuche im 1vs1 deinen Gegner zu droppen.", "§r", "§8§m-------------------------------------").toItemStack();
    public static ItemStack warp5 = new ItemBuilder(Material.BARRIER, 1).setName("§8» §aWarp5").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §aSpawn", "§8➥ §7Teleportiere dich zum Spawn.", "§r", "§8§m-------------------------------------").toItemStack();
    public static ItemStack warp6 = new ItemBuilder(Material.BARRIER, 1).setName("§8» §aWarp6").setLore("§r", "§8§m-------------------------------------", "§r", "§8● §aSpawn", "§8➥ §7Teleportiere dich zum Spawn.", "§r", "§8§m-------------------------------------").toItemStack();
    public static HashMap<TeleporterLocs, Location> teleporterlocs = new HashMap<>();

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 5 * 9, "§8Wähle einen Spielmodus aus.");

        int i = 0;
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack();
        do {
            inv.setItem(i, glass);
            i++;
        } while (i < 5 * 9);

        //if Player has Animated enabled.

        if (p.isBanned()) {

            animate(1, warp1, 0 + 9, inv);
            animate(2, glass, 0 + 9, inv);
            animate(3, warp1, 1 + 9, inv);
            animate(4, glass, 1 + 9, inv);
            animate(5, warp1, 2 + 9, inv);

            animate(6, warp2, 0 + 9, inv);
            animate(7, glass, 0 + 9, inv);
            animate(8, warp2, 1 + 9, inv);
            animate(9, glass, 1 + 9, inv);
            //animate(10, warp2, 2+9, inv);
            //animate(11, glass, 2+9, inv);
            animate(12, warp2, 3 + 9, inv);
            animate(13, glass, 3 + 9, inv);
            animate(14, warp2, 4 + 9, inv);
            animate(15, glass, 4 + 9, inv);
            animate(16, warp2, 5 + 9, inv);
            animate(17, glass, 5 + 9, inv);
            animate(18, warp2, 6 + 9, inv);

            animate(19, warp3, 0 + 9 + 9, inv);
            animate(20, glass, 0 + 9 + 9, inv);
            animate(21, warp3, 1 + 9 + 9, inv);

            animate(22, spawn, 0 + 9 + 9, inv);
            animate(23, glass, 0 + 9 + 9, inv);
            //animate(24, spawn, 1+9+9, inv);
            //animate(25, glass, 1+9+9, inv);
            animate(26, spawn, 2 + 9 + 9, inv);
            animate(27, glass, 2 + 9 + 9, inv);
            animate(28, spawn, 3 + 9 + 9, inv);
            animate(29, glass, 3 + 9 + 9, inv);
            animate(30, spawn, 4 + 9 + 9, inv);


            animate(31, warp4, 0 + 9 + 9, inv);
            animate(32, glass, 0 + 9 + 9, inv);
            //animate(33, warp4, 1+9+9, inv);
            //animate(34, glass, 1+9+9, inv);
            animate(35, warp4, 2 + 9 + 9, inv);
            animate(36, glass, 2 + 9 + 9, inv);
            animate(37, warp4, 3 + 9 + 9, inv);
            animate(38, glass, 3 + 9 + 9, inv);
            //animate(39, warp4, 4+9+9, inv);
            //animate(40, glass, 4+9+9, inv);
            animate(41, warp4, 5 + 9 + 9, inv);
            animate(42, glass, 5 + 9 + 9, inv);
            animate(43, warp4, 6 + 9 + 9, inv);
            animate(44, glass, 6 + 9 + 9, inv);
            animate(45, warp4, 7 + 9 + 9, inv);

            animate(46, warp5, 0 + 9 + 9 + 9, inv);
            animate(47, glass, 0 + 9 + 9 + 9, inv);
            animate(48, warp5, 1 + 9 + 9 + 9, inv);
            animate(49, glass, 1 + 9 + 9 + 9, inv);
            animate(50, warp5, 2 + 9 + 9 + 9, inv);

            animate(51, warp6, 0 + 9 + 9 + 9, inv);
            animate(52, glass, 0 + 9 + 9 + 9, inv);
            animate(53, warp6, 1 + 9 + 9 + 9, inv);
            animate(54, glass, 1 + 9 + 9 + 9, inv);
            //animate(55, warp2, 2+9+9+9, inv);
            //animate(56, glass, 2+9+9+9, inv);
            animate(57, warp6, 3 + 9 + 9 + 9, inv);
            animate(58, glass, 3 + 9 + 9 + 9, inv);
            animate(59, warp6, 4 + 9 + 9 + 9, inv);
            animate(60, glass, 4 + 9 + 9 + 9, inv);
            animate(61, warp6, 5 + 9 + 9 + 9, inv);
            animate(62, glass, 5 + 9 + 9 + 9, inv);
            animate(63, warp6, 6 + 9 + 9 + 9, inv);


        } else {
            inv.setItem(4 + 9 + 9, spawn);
            inv.setItem(2 + 9 + 9, warp1);
            inv.setItem(1 + 9 + 9, warp2);
            inv.setItem(6 + 9 + 9, warp3);
            inv.setItem(7 + 9 + 9, warp4);
//			inv.setItem(2+9+9+9, warp5);
//			inv.setItem(6+9+9+9, warp6);

        }

        p.openInventory(inv);
    }

    public static void animate(int ticks, ItemStack im, int slots, Inventory inv) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                inv.setItem(slots, im);
            }
        }, ticks);
    }

    public static void loadLocs() {
        for (TeleporterLocs loc : TeleporterLocs.values()) {
            Location locx = ConfigManager.getLocation(Main.cfg, loc.toString());
            if (locx != null) {
                teleporterlocs.put(loc, locx);
            }
        }
    }

    public static void teleport(Location loc, Player p) {
        p.closeInventory();
        Vector v = p.getLocation().getDirection().multiply(0.0D).setY(1.2D);
        p.setVelocity(v);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, 255, false, false));
        Main.sm.sound(Sound.STEP_STONE, p);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                p.removePotionEffect(PotionEffectType.BLINDNESS);
                p.teleport(loc);
                Main.sm.sound(Sound.GLASS, p);
                p.playEffect(p.getLocation(), Effect.CLOUD, 500);
                p.playEffect(p.getLocation(), Effect.CLOUD, 500);
                p.playEffect(p.getLocation(), Effect.CLOUD, 500);
                p.playEffect(p.getLocation(), Effect.CLOUD, 500);
                p.playEffect(p.getLocation(), Effect.CLOUD, 500);
                p.playEffect(p.getLocation(), Effect.CLOUD, 500);
            }
        }, 15);
    }

    public static enum TeleporterLocs {
        warp1, warp2, warp3, warp4, warp5, warp6, spawn;
    }

}
