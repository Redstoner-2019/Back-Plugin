package me.redstoner2019.backplugin.Events;

import me.redstoner2019.backplugin.Main;
import me.redstoner2019.backplugin.Misc.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.redstoner2019.backplugin.Main.pluginversion;

public class playerJoinEvent implements Listener {
    @EventHandler
    public static void onPlayerJoinEvent(PlayerJoinEvent event){
        if(event.getPlayer().isOp()){
            Main.checker.getVersion(version -> {
                if (Main.plugin.getDescription().getVersion().equals(pluginversion)) {
                    event.getPlayer().sendMessage(ChatColor.GREEN + "[Back Plugin] Plugin is up to of date!");
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "[Back Plugin] Plugin is out of date! Your Version: " + pluginversion);
                }
            });
        }
    }
}
