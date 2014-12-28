package com.grim.spnrpg;

import com.grim.spnrpg.fasttravel.WarpMenu;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin{

    public static Main plugin;
    private static Economy econ = null;
    private static Permission perms = null;
    private FileConfiguration warps = null;
    private File warpsFile = null;
    private IconMenu warpMenu;

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

    public Main getInstance(){
        return plugin;
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
}
