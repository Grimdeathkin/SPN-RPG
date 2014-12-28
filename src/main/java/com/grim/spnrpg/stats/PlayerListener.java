package com.grim.spnrpg.stats;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerListener implements Listener {

    private Main plugin;

    public PlayerListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        ConfigHandler configHandler = new ConfigHandler(player.getUniqueId().toString() + ".yml");
        FileConfiguration configuration = configHandler.getConfig();

        if(!configuration.contains("stats")){
            setDefaultStats(configHandler);
        }
        HashMap<String, Stats> playerStats;
        playerStats = plugin.getPlayerStats();
        playerStats.put(player.getUniqueId().toString(), getStats(configuration));
        plugin.setPlayerStats(playerStats);

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){
        String uuid = event.getPlayer().getUniqueId().toString();
        FileConfiguration configuration = new ConfigHandler(uuid + ".yml").getConfig();
        Stats stats = plugin.getPlayerStat(uuid.toString());
        configuration.set("stats.stamina", stats.getStamina());
        configuration.set("stats.strength", stats.getStrength());
        configuration.set("stats.dexterity", stats.getDexterity());
        configuration.set("stats.agility", stats.getAgility());
    }

    private void setDefaultStats(ConfigHandler configurationHandler){
        FileConfiguration configuration = configurationHandler.getConfig();
        configuration.addDefault("stats.stamina", 20);
        configuration.addDefault("stats.strength", 1);
        configuration.addDefault("stats.dexterity", 1);
        configuration.addDefault("stats.agility", 1);
        configuration.options().copyDefaults(true);
        configurationHandler.saveConfig();
    }

    private Stats getStats(FileConfiguration configuration){
        int stamina = configuration.getInt("stats.stamina");
        int strength = configuration.getInt("stats.strength");
        int dexterity = configuration.getInt("stats.dexterity");
        int agility = configuration.getInt("stats.agility");

        return new Stats(stamina, strength, dexterity, agility);
    }

}
