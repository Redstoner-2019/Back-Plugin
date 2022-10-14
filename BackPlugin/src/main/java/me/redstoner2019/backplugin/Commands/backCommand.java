package me.redstoner2019.backplugin.Commands;

import me.redstoner2019.backplugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class backCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(Main.config.getBoolean("permissions-enabled")){
            if(p.hasPermission(Main.config.getString("permission"))){
                if(Main.config.getBoolean("teleport-costs") && Main.economyfunctions){
                    if(p.isOp() && Main.config.getBoolean("op-bypasses-cost")){

                    } else {
                        if(Main.econ.getBalance(p) >= Main.config.getDouble("teleport-price")){
                            Main.econ.withdrawPlayer(p,Main.config.getDouble("teleport-price"));
                            p.sendMessage(Main.getMessage("messages.economy.withdraw-on-teleport").replace("%cost%",String.valueOf(Main.config.getDouble("teleport-price"))));
                        } else {
                            p.sendMessage(Main.getMessage("messages.economy.not-enough-money").replace("%cost%",String.valueOf(Main.config.getDouble("teleport-price"))));
                        }
                    }
                }
                if(!Main.lastPoints.containsKey(p)){
                    p.sendMessage(Main.getMessage("messages.death.noPoint"));
                    return false;
                }
                if(Main.lastPoints.get(p).size()>0){
                    p.teleport(Main.lastPoints.get(p).get(0));
                    Main.lastPoints.get(p).remove(0);
                    p.sendMessage(Main.getMessage("messages.death.teleportToLastDeathLocation"));
                } else {
                    p.sendMessage(Main.getMessage("messages.death.noPoint"));
                }
            } else {
                p.sendMessage(Main.getMessage("messages.fail.noPermission"));
            }
        } else {
            if(Main.config.getBoolean("teleport-costs") && Main.economyfunctions){
                if(p.isOp() && Main.config.getBoolean("op-bypasses-cost")){

                } else {
                    if(Main.econ.getBalance(p) >= Main.config.getDouble("teleport-price")){
                        Main.econ.withdrawPlayer(p,Main.config.getDouble("teleport-price"));
                        p.sendMessage(Main.getMessage("messages.economy.withdraw-on-teleport").replace("%cost%",String.valueOf(Main.config.getDouble("teleport-price"))));
                    } else {
                        p.sendMessage(Main.getMessage("messages.economy.not-enough-money").replace("%cost%",String.valueOf(Main.config.getDouble("teleport-price"))));
                    }
                }
            }
            if(!Main.lastPoints.containsKey(p)){
                p.sendMessage(Main.getMessage("messages.death.noPoint"));
                return false;
            }
            if(Main.lastPoints.get(p).size()>0){
                p.teleport(Main.lastPoints.get(p).get(0));
                Main.lastPoints.get(p).remove(0);
                p.sendMessage(Main.getMessage("messages.death.teleportToLastDeathLocation"));
            } else {
                p.sendMessage(Main.getMessage("messages.death.noPoint"));
            }
        }
        return false;
    }
}
