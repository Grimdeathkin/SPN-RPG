package com.grim.spnrpg;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class ConfigHandler {

    private FileConfiguration warps = null;
    private File warpsFile = null;
    private Main plugin = Main.plugin;

    public ConfigHandler(){
        reloadWarpConfig();
    }

    public void reloadWarpConfig() {
        if (warpsFile == null) {
            warpsFile = new File(plugin.getDataFolder(), "customConfig.yml");
        }
        warps = YamlConfiguration.loadConfiguration(warpsFile);

        // Look for defaults in the jar
        try{
            Reader defConfigStream = new InputStreamReader(plugin.getResource("customConfig.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                warps.setDefaults(defConfig);
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public FileConfiguration getWarps() {
        if (warps == null) {
            reloadWarpConfig();
        }
        return warps;
    }

    public void saveWarps() {
        if (warps == null || warpsFile == null) {
            return;
        }
        try {
            getWarps().save(warpsFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + warpsFile, ex);
        }
    }
}
