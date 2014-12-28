package com.grim.spnrpg;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class ConfigHandler {
    private final String fileName;
    private final Main plugin = Main.plugin;

    private FileConfiguration config = null;
    private File configFile = null;

    public ConfigHandler(String fileName){
        this.fileName = fileName;
        configFile = new File(plugin.getDataFolder(), fileName);
    }

    public void reloadWarpConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "customConfig.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            config.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadWarpConfig();
        }
        return config;
    }

    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }else{
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }
}
