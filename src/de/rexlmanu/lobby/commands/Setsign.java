package de.rexlmanu.lobby.commands;

import de.rexlmanu.lobby.methods.Message;
import de.rexlmanu.lobby.methods.SignManager;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by rexlManu on 22.07.2017.
 * Plugin by rexlManu
 * https://rexlGames.de
 * Coded with IntelliJ
 */
public class Setsign implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(Message.CONSOLESENDER.getValue());
            return true;
        }
        Player p = (Player) commandSender;
        if(!p.hasPermission("lobby.setsign")){
            commandSender.sendMessage(Message.NOPERMISSION.getValue());
            return true;
        }
        if(strings.length == 0){
            p.sendMessage(Message.PREFIX.getValue()+"§7Bitte nutze /Setsign <Spielmodus> <Servername> <Port> <IP>");
        }else if(strings.length == 4){
            String spielmodus = strings[0];
            String servername = strings[1];
            String ip= strings[3];
            int port = 0;
            try{
                port = Integer.parseInt(strings[2]);
            }catch(NumberFormatException e){
                p.sendMessage(Message.PREFIX.getValue()+"§7Bitte verwende nur Zahlen.");
                return true;
            }

            SignManager signManager = new SignManager();
            signManager.setValues(servername, spielmodus, port, ip, (Sign) p.getTargetBlock((Set<Material>) null, 10).getLocation().getBlock().getState());
            signManager.saveToConfig();

            p.sendMessage(Message.PREFIX.getValue()+"§7Du hast ein Sign erfolgreich erstellt.");

        } else{
            p.sendMessage(Message.PREFIX.getValue()+"§7Bitte nutze /Setsign <Spielmodus> <Servername> <Port> <IP>");
        }

        return false;
    }
}
