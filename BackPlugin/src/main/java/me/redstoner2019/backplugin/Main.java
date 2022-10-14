package me.redstoner2019.backplugin;

import me.redstoner2019.backplugin.Commands.backCommand;
import me.redstoner2019.backplugin.Events.playerDeathEvent;
import me.redstoner2019.backplugin.Events.playerJoinEvent;
import me.redstoner2019.backplugin.Events.playerTeleportEvent;
import me.redstoner2019.backplugin.Misc.Metrics;
import me.redstoner2019.backplugin.Misc.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static HashMap<Player, List<Location>> lastPoints = new HashMap<>();
    public static YamlConfiguration config;
    public static UpdateChecker checker;
    public static JavaPlugin plugin;
    public static String pluginversion = "1.6.0";
    public static Economy econ = null;
    public static boolean economyfunctions = false;

    @Override
    public void onEnable() {
        economyfunctions = setupEconomy();
        try{
            if(!setupEconomy()){
                this.getLogger().log(Level.INFO,"No vault/economy plugin detected! Disabling all Economy features!");
                economyfunctions = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        plugin = this;
        checker = new UpdateChecker(this, 93348);
        new UpdateChecker(this, 93348).getVersion(version -> {
            if (this.getDescription().getVersion().equals(pluginversion)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });
        //saveConfig();
        saveResource("languages\\lang_EN.yml",false);
        saveResource("config.yml",false);
        getServer().getPluginManager().registerEvents(new playerDeathEvent(),this);
        getServer().getPluginManager().registerEvents(new playerJoinEvent(),this);
        getServer().getPluginManager().registerEvents(new playerTeleportEvent(),this);
        getServer().getPluginCommand("back").setExecutor(new backCommand());
        config = YamlConfiguration.loadConfiguration(new File("plugins//BackPlugin//config.yml"));
        if(!config.isSet("language")){
            config.set("language","EN");
        }
        int pluginId = 13465;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SingleLineChart("players", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return Bukkit.getOnlinePlayers().size();
            }
        }));
    }

    @Override
    public void onDisable() {

    }

    public static String getMessage(String path){
        YamlConfiguration configLang = YamlConfiguration.loadConfiguration(new File("plugins//BackPlugin//languages//lang_" + config.getString("language") + ".yml"));
        if(configLang.getString(path) == null){
            return "Error in " + path + " (Back Plugin). Please notify the Server owner/admin!";
        } else {
            return ChatColor.translateAlternateColorCodes('&',configLang.getString(path));
        }
    }
    public static List<String> getMessageList(String path){
        YamlConfiguration configLang = YamlConfiguration.loadConfiguration(new File("plugins//BackPlugin//languages//lang_" + config.getString("language") + ".yml"));
        if(configLang.getString(path) == null){
            return new ArrayList<String>();
        } else {
            return configLang.getStringList(path);
        }
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
