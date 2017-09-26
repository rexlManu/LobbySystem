package de.rexlmanu.lobby.methods;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;

import java.io.IOException;

/**
 * Created by rexlManu on 23.07.2017.
 * Plugin by rexlManu
 * https://rexlGames.de
 * Coded with IntelliJ
 */
public class SignManager{

    public SignManager instance;

    public String servername;
    public String spielmodus;
    public int port;
    public String ip;
    public Sign sign;

    public SignManager(){
        instance = this;
    }
    public void setValues(String servername, String spielmodus, int port, String ip,Sign sign){
        this.servername = servername;
        this.spielmodus = spielmodus;
        this.port = port;
        this.ip = ip;
        this.sign = sign;
    }
    public void saveToConfig(){
        SignLoader.cfg.set(this.servername+".X", sign.getLocation().getX());
        SignLoader.cfg.set(this.servername+".Y", sign.getLocation().getY());
        SignLoader.cfg.set(this.servername+".Z", sign.getLocation().getZ());
        SignLoader.cfg.set(this.servername+".W", sign.getLocation().getWorld().getName());
        SignLoader.cfg.set(this.servername+".IP", this.ip);
        SignLoader.cfg.set(this.servername+".Port", this.port);
        SignLoader.cfg.set(this.servername+".Mode", this.spielmodus);
        SignLoader.cfg.set(this.servername+".Server", this.servername);
        try {
            SignLoader.cfg.save(SignLoader.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SignLoader.names.add(this.servername);
        SignLoader.updateNames();
    }
    public SignManager getFromConfig(String servername){
        SignManager signManager = new SignManager();
        String spielmodus = SignLoader.cfg.getString(servername+".Mode");
        String ip = SignLoader.cfg.getString(servername+".IP");
        int port = SignLoader.cfg.getInt(servername+".Port");
        Location loc = new Location(Bukkit.getWorld(SignLoader.cfg.getString(servername + ".W")), SignLoader.cfg.getDouble(servername + ".X"), SignLoader.cfg.getDouble(servername + ".Y"), SignLoader.cfg.getDouble(servername + ".Z"));
        Sign sign = (Sign) loc.getBlock().getState();
        signManager.setValues(servername, spielmodus, port, ip, sign);
        return signManager;
    }
}
