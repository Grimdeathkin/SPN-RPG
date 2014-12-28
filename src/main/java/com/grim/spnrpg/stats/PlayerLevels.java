package com.grim.spnrpg.stats;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.Main;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerLevels implements Listener{

    private final Main plugin;
    private final FileConfiguration configuration;
    private final WorldGuardPlugin wgPlugin;
    private final ConfigHandler configHandler;

    public PlayerLevels(Main plugin) {
        this.plugin = plugin;
        configHandler = new ConfigHandler("xp.yml");
        configuration = configHandler.getConfig();
        wgPlugin = plugin.getWorldGuard();
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();
        Stats playerStat = plugin.getPlayerStat(player);
        playerStat.addXP(getXP(entity));
        plugin.getPlayerStats().put(player.getUniqueId().toString(), playerStat);
        configHandler.saveConfig();
    }

    private int getXP(Entity entity){
        for(ProtectedRegion region : getRegions(entity)){
            //Check if a xp value is assigned to the region
            if(configuration.contains(region.getId())){
                return configuration.getInt(region.getId());
            }
        }
        //Return 0 if nothing found
        return 0;
    }

    private ApplicableRegionSet getRegions(Entity entity){
        RegionManager manager = wgPlugin.getRegionManager(entity.getWorld());
        return manager.getApplicableRegions(entity.getLocation());
    }
}
