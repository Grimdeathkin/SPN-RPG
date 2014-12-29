package com.grim.spnrpg;

import com.grim.spnrpg.fasttravel.WarpMenu;
import com.grim.spnrpg.stats.Stats;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin{

    public static Main plugin;
    private static Economy econ = null;
    private static Permission perms = null;
    private IconMenu warpMenu;
    private HashMap<String, Stats> playerStats = new HashMap<String, Stats>();

    @Override
    public void onDisable() {
        plugin = null;
    }

    @Override
    public void onEnable() {
        plugin = this;
        new ListenerRegister(plugin);
        new CommandRegister(plugin);
        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        warpMenu = new WarpMenu(plugin).getIconMenu();
    }

    private boolean setupEconomy(){
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

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public Economy getEcon() {
        return econ;
    }

    public Permission getPerms() {
        return perms;
    }

    public WorldGuardPlugin getWorldGuard(){
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            getServer().getPluginManager().disablePlugin(this);
        }
        return (WorldGuardPlugin) plugin;
    }

    public IconMenu getWarpMenu() {
        return warpMenu;
    }

    public Stats getPlayerStat(Player player){
        return playerStats.get(player.getUniqueId().toString());
    }

    public Stats getPlayerStat(String uuid){
        return playerStats.get(uuid);
    }

    public void updatePlayerStats(Player player, Stats stats){
        ConfigHandler configHandler = new ConfigHandler(player.getUniqueId().toString() + ".yml");
        FileConfiguration configuration = configHandler.getConfig();
        configuration.set("stats.stamina", stats.getStamina());
        configuration.set("stats.strength", stats.getStrength());
        configuration.set("stats.dexterity", stats.getDexterity());
        configuration.set("stats.agility", stats.getAgility());
        configuration.set("stats.level", stats.getLevel());
        configuration.set("stats.xp", stats.getLevel());
        configHandler.saveConfig();
        playerStats.put(player.getUniqueId().toString(), stats);

    }

    public void removePlayerStats(Player player){
        ConfigHandler configHandler = new ConfigHandler(player.getUniqueId().toString() + ".yml");
        FileConfiguration configuration = configHandler.getConfig();
        Stats stats = getPlayerStat(player);
        configuration.set("stats.stamina", stats.getStamina());
        configuration.set("stats.strength", stats.getStrength());
        configuration.set("stats.dexterity", stats.getDexterity());
        configuration.set("stats.agility", stats.getAgility());
        configuration.set("stats.level", stats.getLevel());
        configuration.set("stats.xp", stats.getLevel());
        configHandler.saveConfig();
        playerStats.remove(player.getUniqueId().toString());
    }

    public HashMap<String, Stats> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(HashMap<String, Stats> playerStats) {
        this.playerStats = playerStats;
    }
}
