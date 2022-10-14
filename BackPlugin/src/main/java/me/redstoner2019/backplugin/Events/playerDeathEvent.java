package me.redstoner2019.backplugin.Events;

import me.redstoner2019.backplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

public class playerDeathEvent implements Listener {
    @EventHandler
    public static void onPlayerDeathEvent(PlayerDeathEvent event){
        Player p = event.getEntity();
        Location deathLocation = event.getEntity().getLocation();
        if(!Main.lastPoints.containsKey(p)){
            Main.lastPoints.put(p,new ArrayList<>());
        }
        Main.lastPoints.get(p).add(deathLocation);
        for(String s : Main.getMessageList("messages.death.onDeathMessages")){
            s = s.replace("%x%", String.valueOf(deathLocation.getBlockX()));
            s = s.replace("%y%", String.valueOf(deathLocation.getBlockY()));
            s = s.replace("%z%", String.valueOf(deathLocation.getBlockZ()));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
        }
        if(Main.lastPoints.get(p).size()>Main.config.getInt("max-positions-count")){
            Main.lastPoints.get(p).remove(10);
        }
    }
}
