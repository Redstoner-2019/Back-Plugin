package me.redstoner2019.deathplugin.deathplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


public final class Main extends JavaPlugin implements Listener {

    int test; // Ganzzahlen zwischen -2 147 483 648 und 2 147 483 647


    public HashMap<Player, Location> positions = new HashMap<>();
    public HashMap<Player, Boolean> disabled = new HashMap<>();













    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginCommand("back").setExecutor(this);

        File file = new File("plugins//backPlugin//config.yml");
        YamlConfiguration yamlConfiguration =  YamlConfiguration.loadConfiguration(file);

        if(!yamlConfiguration.isSet("permissions-enabled")){
            yamlConfiguration.set("permissions-enabled",true);
        }
        if(!yamlConfiguration.isSet("permission")){
            yamlConfiguration.set("permission","back.back");
        }
        if(!yamlConfiguration.isSet("teleport-support")){
            yamlConfiguration.set("teleport-support",true);
        }
        if(!yamlConfiguration.isSet("teleport-support-needs-permission")){
            yamlConfiguration.set("teleport-support-needs-permission",false);
        }
        if(!yamlConfiguration.isSet("teleport-support-permission")){
            yamlConfiguration.set("teleport-support-permission","back.teleport");
        }
        if(!yamlConfiguration.isSet("teleport-minimum-distance")){
            yamlConfiguration.set("teleport-minimum-distance",50.0);
        }
        try {
            yamlConfiguration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        int pluginId = 13465; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        // Optional: Add custom charts
        //metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        metrics.addCustomChart(new Metrics.SingleLineChart("players", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return Bukkit.getOnlinePlayers().size();
            }
        }));





        //Bukkit.broadcastMessage(String.valueOf(file.length()));
        File languageFile = new File("plugins//backPlugin//languages//lang_ENGLISH.yml");
        YamlConfiguration yamlConfigurationlang =  YamlConfiguration.loadConfiguration(languageFile);
        try {
            File myObj = new File("plugins//backPlugin//languages//lang_ENGLISH.yml");
            if (myObj.createNewFile()) {

            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!yamlConfigurationlang.isSet("messages")) {
            FileWriter writer = null;
            new File("plugins//backPlugin//languages//").mkdirs();
            try {
                //Bukkit.broadcastMessage("Language File is getting Generated...");
                writer = new FileWriter(languageFile);
                writer.write("messages:\n" +
                        "  death:\n" +
                        "    noPoint: '&cYou don''t have a last position!'\n" +
                        "    teleportToLastDeathLocation: '&5You got teleported to your last death point!'\n" +
                        "    onDeathMessages:\n" +
                        "      - '&6You died!'\n" +
                        "      - '&bYour last coordinates were: %x% %y% %z% '\n" +
                        "  teleport:\n" +
                        "    noPoint: '&cYou don''t have a last position!'\n" +
                        "    teleportToLastTeleportLocation: '&5You got teleported to your last point!'\n" +
                        "    onTeleportMessages:\n" +
                        "      - '&6You teleported!'\n" +
                        "      - '&bYour last coordinates were: %x% %y% %z%'\n" +
                        "  fail:\n" +
                        "    noPermission: '&6You don''t have permission to use this command!'\n");
                writer.close();
                //Bukkit.broadcastMessage("Language File Generated.");
            } catch (IOException e) {
                //Bukkit.broadcastMessage("Language File Generation Failed!");
                //System.out.println("Error");
                e.printStackTrace();
                String error = "Error in Language file " + "plugins//backPlugin//languages//lang_" + yamlConfiguration.getString("language") + ".yml";
            }
        }







        String mcVersion = getServer().getVersion();
        //Bukkit.broadcastMessage("Version: " + mcVersion);
        metrics.addCustomChart(new Metrics.DrilldownPie("Minecraft Version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String javaVersion = System.getProperty("java.version");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (mcVersion.contains("1.7")) {
                map.put("Minecraft 1.7", entry);
            } else if (mcVersion.contains("1.8")) {
                map.put("Minecraft 1.8", entry);
            } else if (mcVersion.contains("1.9")) {
                map.put("Minecraft 1.9", entry);
            } else if (mcVersion.contains("1.10")) {
                map.put("Minecraft 1.10", entry);
            }else if (mcVersion.contains("1.11")) {
                map.put("Minecraft 1.11", entry);
            }else if (mcVersion.contains("1.12")) {
                map.put("Minecraft 1.12", entry);
            }else if (mcVersion.contains("1.13")) {
                map.put("Minecraft 1.13", entry);
            }else if (mcVersion.contains("1.14")) {
                map.put("Minecraft 1.14", entry);
            }else if (mcVersion.contains("1.15")) {
                map.put("Minecraft 1.15", entry);
            }else if (mcVersion.contains("1.16")) {
                map.put("Minecraft 1.16", entry);
            }else if (mcVersion.contains("1.17")) {
                map.put("Minecraft 1.17", entry);
            }else if (mcVersion.contains("1.18")) {
                map.put("Minecraft 1.18", entry);
            }else {
                map.put("Other Minecraft Version", entry);
            }
            return map;
        }));



        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String javaVersion = System.getProperty("java.version");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (javaVersion.startsWith("1.7")) {
                map.put("Java 1.7", entry);
            } else if (javaVersion.startsWith("1.8")) {
                map.put("Java 1.8", entry);
            } else if (javaVersion.startsWith("1.9")) {
                map.put("Java 1.9", entry);
            } else {
                map.put("Other", entry);
            }
            return map;
        }));




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        for(String s : getMessages("messages.death.onDeathMessages")){
            s = s.replaceAll("%x%",String.valueOf(player.getLocation().getBlockX()));
            s = s.replaceAll("%y%",String.valueOf(player.getLocation().getBlockY()));
            s = s.replaceAll("%z%",String.valueOf(player.getLocation().getBlockZ()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
        }
        event.setDeathMessage(ChatColor.RED + event.getDeathMessage());
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try{
                    positions.put(player, player.getLocation());
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread t = new Thread(runnable2);
        t.start();
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        File file = new File("plugins//backPlugin//config.yml");
        YamlConfiguration yamlConfiguration =  YamlConfiguration.loadConfiguration(file);
        if(yamlConfiguration.getBoolean("teleport-support")){
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();
            //player.sendMessage("Distance = " +  + " Blöcke.");
            Double distance = from.distance(to);
            if(distance <= yamlConfiguration.getDouble("teleport-minimum-distance")){
                return;
            }
            if(yamlConfiguration.getBoolean("teleport-support-needs-permission")){
                if(player.isOp() || player.hasPermission(yamlConfiguration.getString("teleport-support-permission"))){
                    for(String s : getMessages("messages.teleport.onTeleportMessages")){
                        s = s.replaceAll("%x%",String.valueOf(player.getLocation().getBlockX()));
                        s = s.replaceAll("%y%",String.valueOf(player.getLocation().getBlockY()));
                        s = s.replaceAll("%z%",String.valueOf(player.getLocation().getBlockZ()));
                        //from = event.getFrom();
                        //to = event.getTo();
                        //s = s.replaceAll("%distance%",String.valueOf(from.distance(to)));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
                    }
                    //player.sendMessage(ChatColor.DARK_RED + "You teleported!");
                    //player.sendMessage(ChatColor.AQUA + "Your last coordinates were: " + ChatColor.GOLD +  player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + " ");
                    Runnable runnable2 = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                positions.put(player, event.getFrom());
                                Thread.sleep(500);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    Thread t = new Thread(runnable2);
                    t.start();

                }
            } else {
                for(String s : getMessages("messages.teleport.onTeleportMessages")){
                    s = s.replaceAll("%x%",String.valueOf(player.getLocation().getBlockX()));
                    s = s.replaceAll("%y%",String.valueOf(player.getLocation().getBlockY()));
                    s = s.replaceAll("%z%",String.valueOf(player.getLocation().getBlockZ()));
                    //from = event.getFrom();
                    //to = event.getTo();
                    //s = s.replaceAll("%distance%",String.valueOf(from.distance(to)));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
                }
                //player.sendMessage(ChatColor.DARK_RED + "You teleported!");
                //player.sendMessage(ChatColor.AQUA + "Your last coordinates were: " + ChatColor.GOLD +  player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + " ");
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        try{
                            positions.put(player, event.getFrom());
                            Thread.sleep(500);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                Thread t = new Thread(runnable2);
                t.start();
            }


        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player p = event.getPlayer();
        String Version = "1.4.0";
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=93348").openConnection();
            String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (!version.equalsIgnoreCase(Version)) {
                if(p.isOp()){
                    p.sendMessage(ChatColor.RED + "[Back-Plugin] Plugin is out of date! Your Version: " + Version + " , newest Version: " + version);
                    p.sendMessage(ChatColor.GOLD + "Download at https://www.spigotmc.org/resources/back-on-death-plugin-for-1-17.93348/ !");
                    getLogger().warning("Plugin Out of Date");
                }
            } else {
                //p.sendMessage("Plugin Up to Date");
            }
        } catch (Exception e) {
            if(p.isOp()){
                p.sendMessage(ChatColor.RED + "An Fatal error occured! Please report the error in the Console to my Discord Server! discord.gg/CGPAYXRfJe");
            }
            getLogger().info(String.valueOf(e));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            disabled.put(player,true);
            File file = new File("plugins//backPlugin//config.yml");
            YamlConfiguration yamlConfiguration =  YamlConfiguration.loadConfiguration(file);
            File languageFile = new File("plugins//backPlugin//languages//lang_" + yamlConfiguration.getString("language") + ".yml");
            YamlConfiguration yamlConfigurationlang =  YamlConfiguration.loadConfiguration(languageFile);


            if(!yamlConfiguration.isSet("permissions-enabled")){
                yamlConfiguration.set("permissions-enabled",true);
            }
            if(!yamlConfiguration.isSet("permission")){
                yamlConfiguration.set("permission","back.back");
            }
            YamlConfiguration yamlFileConfiguration =  YamlConfiguration.loadConfiguration(file);
            if(yamlConfiguration.getBoolean("permissions-enabled")){
                if(player.hasPermission(yamlConfiguration.getString("permission")) || player.isOp()) {
                    if (positions.containsKey(player)) {
                        player.teleport(positions.get(player));
                        //player.sendMessage(ChatColor.GOLD + "You got teleported to your last death point!");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',getMessage("messages.death.teleportToLastDeathLocation")));
                        positions.remove(player);
                    }else{
                        //player.sendMessage(ChatColor.DARK_RED + "You don't have a last position!");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',getMessage("messages.death.noPoint")));
                    }
                }else{
                    //player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',getMessage("messages.fail.noPermission")));
                }
            } else {
                if (positions.containsKey(player)) {
                    player.teleport(positions.get(player));
                    //player.sendMessage(ChatColor.GOLD + "You got teleported to your last death point!");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',getMessage("messages.death.teleportToLastDeathLocation")));
                    positions.remove(player);
                }else{
                    //player.sendMessage(ChatColor.DARK_RED + "You don't have a last position!");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',getMessage("messages.death.noPoint")));
                }
            }
            try {
                yamlConfiguration.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            Runnable runnable2 = new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    disabled.put(player,false);
                }
            };
            Thread t = new Thread(runnable2);
            t.start();

        }
        return true;
    }
    public static String getMessage(String path){
        File file = new File("plugins//backPlugin//config.yml");
        YamlConfiguration yamlConfiguration =  YamlConfiguration.loadConfiguration(file);
        if(!yamlConfiguration.isSet("language")){
            yamlConfiguration.set("language","ENGLISH");
            try {
                yamlConfiguration.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        File languageFile = new File("plugins//backPlugin//languages//lang_" + yamlConfiguration.getString("language") + ".yml");
        YamlConfiguration yamlLanguageConfiguration =  YamlConfiguration.loadConfiguration(languageFile);
        if(!yamlLanguageConfiguration.isSet("message")){
            //Bukkit.broadcastMessage("No Language File");
            if(yamlConfiguration.getString("language").equals("ENGLISH")){

            } else {
                yamlConfiguration.set("language","ENGLISH");
                languageFile = new File("plugins//backPlugin//languages//lang_" + yamlConfiguration.getString("language") + ".yml");

            }
        }

        String message = yamlLanguageConfiguration.getString(path);
        try {
            yamlConfiguration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            yamlLanguageConfiguration.save(languageFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return message;
    }





    public static List<String> getMessages(String path){
        File file = new File("plugins//backPlugin//config.yml");
        YamlConfiguration yamlConfiguration =  YamlConfiguration.loadConfiguration(file);
        if(!yamlConfiguration.isSet("language")){
            yamlConfiguration.set("language","ENGLISH");
            try {
                yamlConfiguration.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        File languageFile = new File("plugins//backPlugin//languages//lang_" + yamlConfiguration.getString("language") + ".yml");
        if(!languageFile.exists()){
            if(yamlConfiguration.getString("language").equals("ENGLISH")){

            } else {
                yamlConfiguration.set("language","ENGLISH");
                languageFile = new File("plugins//backPlugin//languages//lang_" + yamlConfiguration.getString("language") + ".yml");
                if(!languageFile.exists()){

                }
            }
        }
        YamlConfiguration yamlLanguageConfiguration =  YamlConfiguration.loadConfiguration(languageFile);
        List<String> message = yamlLanguageConfiguration.getStringList(path);
        try {
            yamlConfiguration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            yamlLanguageConfiguration.save(languageFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return message;
    }



}