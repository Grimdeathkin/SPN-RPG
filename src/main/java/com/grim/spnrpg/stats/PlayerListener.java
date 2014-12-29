package com.grim.spnrpg.stats;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        plugin.updatePlayerStats(player, getStats(configuration));

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){
        String uuid = event.getPlayer().getUniqueId().toString();
        ConfigHandler configHandler = new ConfigHandler(uuid + ".yml");
        FileConfiguration configuration = configHandler.getConfig();
        Stats stats = plugin.getPlayerStat(uuid);
        configuration.set("stats.stamina", stats.getStamina());
        configuration.set("stats.strength", stats.getStrength());
        configuration.set("stats.dexterity", stats.getDexterity());
        configuration.set("stats.agility", stats.getAgility());
        configuration.set("stats.level", stats.getLevel());
        configuration.set("stats.xp", stats.getLevel());
        configHandler.saveConfig();
        plugin.removePlayerStats(event.getPlayer());

    }


    @EventHandler
    public void onXPChange(PlayerXPChangeEvent event){
        //TODO Calculate xp required to level up
    }

    private void setDefaultStats(ConfigHandler configurationHandler){
        FileConfiguration configuration = configurationHandler.getConfig();
        configuration.addDefault("stats.level", 1);
        configuration.addDefault("stats.stamina", 20);
        configuration.addDefault("stats.strength", 1);
        configuration.addDefault("stats.dexterity", 1);
        configuration.addDefault("stats.agility", 1);
        configuration.addDefault("stats.xp", 0);
        configuration.options().copyDefaults(true);
        configurationHandler.saveConfig();
    }

    private Stats getStats(FileConfiguration configuration){
        int stamina = configuration.getInt("stats.stamina");
        int strength = configuration.getInt("stats.strength");
        int dexterity = configuration.getInt("stats.dexterity");
        int agility = configuration.getInt("stats.agility");
        int level = configuration.getInt("stats.level");
        double xp = configuration.getDouble("stats.xp");

        return new Stats(stamina, strength, dexterity, agility, level, xp);
    }

}
