package de.rexlmanu.lobby.methods;

import de.rexlmanu.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rexlManu on 23.07.2017.
 * Plugin by rexlManu
 * https://rexlGames.de
 * Coded with IntelliJ
 */
public class SignLoader {

    public static File file = new File(Main.instance.getDataFolder(), "signs.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static ArrayList<SignManager> serverSigns;
    public static List<String> names;

    public static void load(){
        if(!file.exists()){
        try{
            List<String> v = new ArrayList<>();
            file.createNewFile();
            cfg.set("Signs" , v);
        }catch (Exception e){
            e.printStackTrace();
        }

        }
        names = cfg.getStringList("Signs");
        serverSigns = new ArrayList<>();
        for(String servername : names){
            SignManager signManager = new SignManager();
            serverSigns.add(signManager.getFromConfig(servername));
        }

        updateSigns();
    }
    public  static void updateNames(){
        cfg.set("Signs" , names);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void updateSigns(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, () -> {

            for(SignManager signManager : serverSigns){

                Server server = new Server(signManager.ip, signManager.port);
                server.ping();

                if(server.online){
                    signManager.sign.setLine(0, "§0[§a"+signManager.spielmodus+"§0]");
                    signManager.sign.setLine(1, "§0- "+signManager.servername+" §0-");
                    if(server.motd == null || server.motd.equals("")){
                        signManager.sign.setLine(2, "§0➟ §aOnline");
                    }else{
                        signManager.sign.setLine(2, "§0➟ "+server.motd);
                    }
                    signManager.sign.setLine(3, "§0"+server.onlineplayers+" ● "+server.getMaxplayers());
                    signManager.sign.update();
                }else{
                    signManager.sign.setLine(0, "§r");
                    signManager.sign.setLine(1, "§0§oServer");
                    signManager.sign.setLine(2, "§0§oloading");
                    signManager.sign.setLine(3, "§r");
                    signManager.sign.update();
                }

            }

        }, 0, 20);
    }

}
