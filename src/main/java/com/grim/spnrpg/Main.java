package com.grim.spnrpg;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main extends JavaPlugin{

    public static Main plugin;
    private static Economy econ = null;
    private static Permission perms = null;
    private FileConfiguration warps = null;
    private File warpsFile = null;

    @Override
    public void onDisable() {
        plugin = null;
    }

    @Override
    public void onEnable() {
        plugin = this;
        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        new ConfigHandler();
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
}
