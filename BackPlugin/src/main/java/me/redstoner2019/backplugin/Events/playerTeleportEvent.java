package me.redstoner2019.backplugin.Events;

import me.redstoner2019.backplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;

public class playerTeleportEvent  implements Listener {
    @EventHandler
    public static void onPlayerTeleportEvent(PlayerTeleportEvent event){
        Player p = event.getPlayer();
        Location deathLocation = event.getPlayer().getLocation();
        if(!Main.lastPoints.containsKey(p)){
            Main.lastPoints.put(p,new ArrayList<>());
        }
        Main.lastPoints.get(p).add(deathLocation);
        for(String s : Main.getMessageList("messages.teleport.onTeleportMessages")){
            s = s.replace("%x%", String.valueOf(deathLocation.getBlockX()));
            s = s.replace("%y%", String.valueOf(deathLocation.getBlockY()));
            s = s.replace("%z%", String.valueOf(deathLocation.getBlockZ()));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
        }
    }
}
